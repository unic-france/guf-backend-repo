package com.unic.fr.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guf.batch.data.entity.Partner;
import com.unic.fr.exception.TechnicalException;
import com.unic.fr.mail.MailInitPassword;
import com.unic.fr.mail.MailService;
import com.unic.fr.message.request.ForgotPassForm;
import com.unic.fr.message.request.InitPassForm;
import com.unic.fr.message.request.LoginForm;
import com.unic.fr.message.request.SignUpForm;
import com.unic.fr.message.response.JwtResponse;
import com.unic.fr.model.Role;
import com.unic.fr.model.RoleName;
import com.unic.fr.model.User;
import com.unic.fr.repository.PartnerRepository;
import com.unic.fr.repository.PartnercontactRepository;
import com.unic.fr.repository.PartnerprofileRepository;
import com.unic.fr.repository.RoleRepository;
import com.unic.fr.repository.UserRepository;
import com.unic.fr.security.jwt.JwtProvider;


@PropertySource("classpath:mail.properties")
@CrossOrigin(origins = "http://machadi.local:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPI {

	@Value("${mail.initpassword.subject}")
	private String subjectInitPassword;
	
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PartnerRepository partnerRepository;
    
    @Autowired
    PartnercontactRepository partnercontactRepository;
    
    @Autowired
    PartnerprofileRepository partnerprofileRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    MailService mailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
		
		boolean oneTimePassword =  userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get().getOnetimepassword();
        
        return ResponseEntity.ok(new JwtResponse(jwt, oneTimePassword));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        
        String initPassword = generateInitPassword();
        
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getFirstName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(initPassword),true);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
        	switch(role) {
	    		case "admin":
	    			Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	    			roles.add(adminRole);
	    			
	    			break;
	    		case "pm":
	            	Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	            	roles.add(pmRole);
	            	
	    			break;
	    		default:
	        		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	        		roles.add(userRole);        			
        	}
        });
        
        user.setRoles(roles);
        userRepository.save(user);
        
        return ResponseEntity.ok().body("User registered successfully!");
    }
    
    @PostMapping("/updateUserPassword")
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody InitPassForm initPassRequest) {
    	
    	ResponseEntity<String> response = null;
    	
    	try {
    		
    		User user =  userRepository.findByUsername(initPassRequest.getUsername()).get();
            
    		user.setPassword(encoder.encode(initPassRequest.getPassword()));
    		user.setOnetimepassword(false);
    		userRepository.saveAndFlush(user);
    		
    	} catch (Exception e) {
			
			System.out.println(e.getMessage());
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		} finally {
			
			if (null == response) {
				response = new ResponseEntity<String>("Le mot de passe a été mis à jour", HttpStatus.OK);
			}
		}
    	return response;
    }
    
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPassForm forgotPassRequest) {
    	
    	ResponseEntity<String> response = null;
    	
    	Partner partner = null;
    	
    	if (null == forgotPassRequest.getEmail() || forgotPassRequest.getEmail().isEmpty()) {
    		return new ResponseEntity<String>("Vous devez fournir une adresse mail valide", HttpStatus.BAD_REQUEST);
    	}
    	
    	try {
    		System.out.println("Partner");
    		partner = partnerRepository.getPartnerByPartnerprofilePartnercontactEmailpartner(forgotPassRequest.getEmail());
    		
    		System.out.println("User");
    		User user = userRepository.findByUuid(partner.getPuid()).get();
            
    		String password = generateInitPassword();
    		
    		user.setPassword(encoder.encode(password));
    		user.setOnetimepassword(true);
    		
    		System.out.println("Mail");
    		sendPasswordByEmail(partner.getPartnerprofile().getPartnercontact().getEmailpartner(),password);
    		
    		System.out.println("Flush");
    		userRepository.saveAndFlush(user);
    		
    		
    		
    	} catch (TechnicalException e) {
    			
    			System.out.println("Mail Exception : "+e.getMessage());
    			response = new ResponseEntity<String>("Echec de l'envoi de l'email d'initialisation. Contacter le support (contact@unic-france.com)", HttpStatus.BAD_REQUEST);
    		
    	} catch (Exception e) {
    		
    		System.out.println(e.getMessage());
    		if (null == partner) {
    			response = new ResponseEntity<String>("Aucun compte associé à cette adresse mail. Contacter le support (contact@unic-france.com)", HttpStatus.BAD_REQUEST);
        	}
    		
    	} finally {
    		
    		if (null == response) {
    			response = new ResponseEntity<String>("L'email de réinitilisation a été envoyé avec succès.", HttpStatus.OK);
    		}
    		
    	}
    	return response;
    }
    
    private void sendPasswordByEmail(String emailpartner, String password) throws TechnicalException {
    	
		MailInitPassword mail = new MailInitPassword(emailpartner, password, subjectInitPassword);
		
		this.mailService.send(mail);
		
	}

	private String generateInitPassword() {
    	
    	PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "ERROR__CODE";
            }

            public String getCharacters() {
                return "[email protected]#$%^&**()__+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
          upperCaseRule, digitRule);
        
        System.out.println("PASSWORD GENERATED : "+password);
        
        return password;
    }
}