package com.unic.fr.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DateUtils {
	
	public static boolean comparePeriod(Date invoicePeriod, String periodParam) {
		
		boolean resultat = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String invoice = sdf.format(invoicePeriod);
		
		if (invoice.equalsIgnoreCase(periodParam)) {
			resultat = true;
		}
		
		return resultat;
		
		
	}

}
