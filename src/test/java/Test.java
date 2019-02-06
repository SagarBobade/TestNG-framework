/*
 * public class Test {
 * 
 * 
 * public static void fun() {
 * 
 * String name = new Object(){}.getClass().getEnclosingMethod().getName();
 * System.out.println(name); } public static void main(String[] args) {
 * 
 * fun();
 * 
 * }
 * 
 * }
 */


public class Test {

	static String methodName;
	
	public static void fun() {
		
		 methodName = new Object() {}
	      .getClass()
	      .getEnclosingMethod()
	      .getName();
	      
		System.out.println(methodName);
	}
	public static void main(String[] args) {

		fun();

	}

}
