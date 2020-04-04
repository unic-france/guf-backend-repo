package com.unic.fr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


public class Utils {
	
	@Autowired
	ModelMapper modelMapper;

	// Generic function to convert set to list
	public static <T> List<T> convertToList(Set<T> set){
		// create an empty list
		List<T> items = new ArrayList<>();

		// push each element in the set into the list
		for (T e : set)
			items.add(e);

		// return the list
		return items;
	}
}
