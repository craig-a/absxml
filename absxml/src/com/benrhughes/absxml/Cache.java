package com.benrhughes.absxml;

import java.util.Hashtable;

/**
 * Provides thread-safe caching of StringBuffer objects
 * @author Ben Hughes
 */

/*
 * Currently Cache is just a thread-safe wrapper for Hashtable, however
 * it has been factored out into its own class to allow further enhancements,
 * eg persistance
 */
public class Cache {

	// ensure writes to _ht are synchronised to ensure threadsafeness
	private static Hashtable <String, StringBuilder> _ht = new Hashtable <String, StringBuilder>();
	
	public static synchronized void add(String key, StringBuilder value){
		_ht.put(key, value);
	}
	
	public static StringBuilder get(String key){
		return _ht.get(key);
	}
	
	private Cache(){
		}
	}
