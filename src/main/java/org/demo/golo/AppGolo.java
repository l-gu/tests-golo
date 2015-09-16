package org.demo.golo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

import fr.insalyon.citi.golo.compiler.GoloClassLoader;

public class AppGolo {

	public static void main(String... args) {
		System.out.println("Hello world!");
		System.out.println("Java version : " + System.getProperty("java.version") );
		
	    invokeGoloFunction("foo.golo", "fct1") ;
	    invokeGoloFunction("foo.golo", "fct2", "Aaa") ;
	    invokeGoloFunction("foo.golo", "fct2", 123) ;
	    invokeGoloFunction("foo.golo", "fct2", new Date()) ;
	    
	    Object r = invokeGoloFunction("foo.golo", "addition", 2, 5) ;
	    System.out.println("Result = " + r + " ( class : " + r.getClass().getSimpleName() + " )");

	    r = invokeGoloFunction("foo.golo", "addition", 2.5, 5.9) ;
	    System.out.println("Result = " + r + " ( class : " + r.getClass().getSimpleName() + " )");

	    //r = invokeGoloFunction("foo.golo", "addition", 2) ; // function 'addition' not found

	    r = invokeGoloFunction("foo.golo", "getDate" ) ;
	    System.out.println("Result = " + r + " ( class : " + r.getClass().getSimpleName() + " )");
	    
	    r = invokeGoloFunction("foo.golo", "getLong" ) ;
	    System.out.println("Result = " + r + " ( class : " + r.getClass().getSimpleName() + " )");
	    
	    r = invokeGoloFunction("foo.golo", "increment", 20 ) ;
	    System.out.println("Result = " + r + " ( class : " + r.getClass().getSimpleName() + " )");

//	    r = invokeGoloFunction("foo.golo", "increment", 12.34 ) ; // Operator plus is not supported for types class java.math.BigDecimal
//	    System.out.println("Result = " + r + " ( class : " + r.getClass().getSimpleName() + " )");

	    BigDecimal bigd = new BigDecimal("-44.55");
	    // bigd.abs()
	    r = invokeGoloFunction("foo.golo", "abs", bigd ) ;
	    System.out.println("Result = " + r + " ( class : " + r.getClass().getSimpleName() + " )");
	    
//	    invokeGoloFunction("foo.golo", "fct", "Aaa", 2, new Date()) ;
	}

	private final static String GOLO_PATH = "D:/workspaces-TELOSYS-TOOLS/wks-43-telosys-tools-bricks/tests-golo/golo-src/" ;
	
	public static Object invokeGoloFunction(String goloModule, String goloFunction, Object... parameters) {

		// a Golo module is viewable as a Java class where each function is a static method.
		String goloModuleAbsolutePath = GOLO_PATH + goloModule ;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream( goloModuleAbsolutePath );
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Golo module '" + goloModuleAbsolutePath + "' not found");
		}
		
	    GoloClassLoader goloClassLoader = new GoloClassLoader();
	    Class<?> goloModuleClass = goloClassLoader.load(goloModule, fileInputStream);
	    
//	    Class<?>[] typesArray = parametersTypes.toArray(new Class<?>[0]);
//	    List<Class<?>> parametersTypes = new ArrayList<>() ;
//	    for ( int i = 0 ; i < parametersCount ; i++ ) {
//	    	parametersTypes.add(Object.class);
//	    }
	    
	    int i = 0 ;
	    Class<?>[] parametersTypes = new Class<?>[parameters.length] ;
	    Object[] parametersValues  = new Object[parameters.length] ;
	    for ( Object parameter : parameters ) {
	    	parametersTypes[i]  = Object.class ; 
	    	parametersValues[i] = parameter ; 
	    	i++;
	    }
	    
	    Method method = null ;
	    try {
	    	method = goloModuleClass.getMethod(goloFunction, parametersTypes );
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Golo function '" + goloFunction + "' not found in module '" + goloModule + "'", e);
		} catch (SecurityException e) {
			throw new RuntimeException("Cannot get golo function '" + goloFunction + "' in module '" + goloModule + "' (SecurityException)", e);
		}
	    
	    Object result = null ;
	    try {
	    	result = method.invoke(null, parametersValues);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("Error calling Golo function '" + goloFunction + "' in module '" + goloModule + "'", e);
		}
	    
	    return result ;
	}
	
}
