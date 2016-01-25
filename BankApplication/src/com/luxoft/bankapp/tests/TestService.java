package com.luxoft.bankapp.tests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.luxoft.bankapp.annotations.NoDB;

class TestService {

    /**

      * This method should analyze the fields o1 and o2.

      * It should compare all the fields with the help of equals,

      * except for those fields that are marked with an annotation

      *  @NoDB

      * And return true, if all the fields are equal.

              * Also it should be able to compare the collections.

      */

    public static boolean isEquals (Object o1, Object o2) {
    		Field[] fields1 = o1.getClass().getDeclaredFields();
    		Field[] fields2 = o2.getClass().getDeclaredFields();
    		
    		for(Field field: fields1)
    		{
    			field.setAccessible(true);
    			Annotation[] annotations = field.getAnnotations();
    			for(Annotation annotation: annotations)
    				if(annotation.annotationType().equals(NoDB.class))
    				{
    					field=null;
    				}
    		}
    		for(Field field: fields2)
    		{
    			field.setAccessible(true);
    			Annotation[] annotations = field.getAnnotations();
    			for(Annotation annotation: annotations)
    				if(annotation.annotationType().equals(NoDB.class))
    				{
    					field=null;
    				}
    		}
    		
    		for(int i=0; i<fields1.length; i++)
    			if(!fields1[i].equals(fields2[i]))
    				return false;
    		
			return true;
    		
    }

}