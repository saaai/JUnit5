package com.in28minutes.JUnit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class StringTest {

	//@BeforeAll for connecting to the database even though if a class has 100 different methods anytime we use @BeforeAll the method has to be static
	@org.junit.jupiter.api.BeforeAll
	static void BeforeAll() {
        System.out.println("Intializing connection to the database:");
	}
	
	//@AfterAll for ending th e connection after finishing testing all the the test cases in the test class file, this all has to be static method 
	@org.junit.jupiter.api.AfterAll
	static void AfterAll() {
        System.out.println("Closing connection to the database:");

	}
	
	//Initializing @BeforeEach test
	@org.junit.jupiter.api.BeforeEach  //In JUnit 4 we call this as @Before
	void BeforeEach(TestInfo info) {
		System.out.println("Initialize Test Data For Each Test");
		System.out.println("Intialize Test Info " + info.getDisplayName() );
	}
	
	/*why is it running 4 times? because there are 4 unit tests present in the test class file 
	 * 	or it will run that many number of times depending on the number of test cases present in the Test File.
	 * 		The way it would be is "BeforeEach" of the unit test the initialization is done, 
	 * 			the test is executed after that the next time Initialization is done and the next test is executed and so on and so forth ......
	*/
	
	//After Each test 
	@org.junit.jupiter.api.AfterEach //In Junit 4 we call it @After
	void AfterEach(TestInfo info) {
		System.out.println("Clean Up Test Data");
		System.out.println("Intialize Test Info " + info.getDisplayName() );
	}
	
	
	@Test
	void basic_length() {
		int actualLength = "ABCD".length();
		int expectedLength = 4 ;
		
		//Assertion (check's in place)
		assertEquals(expectedLength, actualLength);
		
		//fail("Not yet implemented");
	}
	
	@Test
	void toUpperCase() {
		String str = "abcde";
		String result = str.toUpperCase();
		assertEquals("ABCDE", result);	//we dont have to create another variable for expected output we can directly give it as the "ABCD".	
	}
	
	@Test
	void min_max() {
		int num1 = 2;
		int num2 = 4;
		boolean result = num1 < num2;
		boolean expectedResult = true; 
		assertEquals(expectedResult, result );
		assertNotNull(result);
		//assertNull(result); test would fail becos the result is not null
	}
	
	//test for contains
	@Test
	void Contains_Basic() {
		String str = "Hi, How are you?";
		boolean result = str.contains("?");
		assertTrue(result);
		//assertEquals(true, result);
		//assertFalse(result); //we can also use "assertFalse" directly to check if the condition is false
		
		//we can write the whole test code in this way or the above way this procedure is called "inline" which can be found in "Refactor" option or you can just simply use "option+command+i" to make it more readable or to reduce the no. of lines in the code.
		assertTrue("Hi, How are you?".contains("?"));
		assertFalse("Hi, How are you?".contains("Hello")); // false becos it doesnt contain hello
	}
	
	//test for split
	@Test
	void Split_Basic()
	{
		String str = "abc def ghi";
		String[] actualresult = str.split(" "); //the data type of result here is String[] array
	
		String[] expectedresult = new String[] {"abc", "def", "ghi"};//the expectedresult is a new String Array
		
		assertArrayEquals(expectedresult , actualresult);//While writing the assertEquals(  ,  ) the first parameter should always be the ExpectedResult then the second parameter will be ActualResult.
	}
	
	//Now, we write test case for handling Exceptions
	@Test
	@DisplayName("When length is null, throw an exception")
	void length_exception() {
		String str =  null;
		//int actual = str.length();
		assertThrows(NullPointerException.class,
				() -> {
					str.length();
					//we can write any number of lines of code.
					
				}
				);
	}
	
	 	@ParameterizedTest  //we need to annotate it as "@parameterizedTest" instead of @Test.
		@ValueSource(strings = {"ABCD", "A", "AD", "BCD", "def", "Ads", "ALSI", " "})	//for running different pieces of data we need to have "ValueSource( )" we can pass Strings, int, long, or anything data type
		@DisplayName("with parameterized test")
		void length_greater_than_0(String str){

			assertTrue(str.length()>0);
		}
	

	 	@ParameterizedTest
		//Instead of ValueSource when we have multiple parameters to be passed into a test, we can call something called a "CsvSource(value = {"" , "", "", })". We can add any number of values/set of values. Since, it's an array we can add in any number of values. How do we add values in here? One of the important things is we have to check the parameters like: String, int, or any other data type values

	 	@CsvSource(value = {"abcd,  ABCD", "ab, AB", "d, D", "a, A", " abE, ABE", " ' ', ' '  "})
		void UpperCase(String word, String capitalizedWord) 
		//(the word we want to check and the word when it is coverted into capitalized word)
	 	{
			assertEquals(capitalizedWord, word.toUpperCase());
	 	}

	 	@ParameterizedTest
		//Instead of ValueSource when we have multiple parameters to be passed into a test, we can call something called a "CsvSource(value = {"" , "", "", })". We can add any number of values/set of values. Since, it's an array we can add in any number of values. How do we add values in here? One of the important things is we have to check the parameters like: String, int, or any other data type values

	 	@CsvSource(value = {"abcde,  5", "ab, 2", "d, 1", "a, 1", " abE, 3", "'',0 "})
		void Length(String word, int ExpectedLength) 
		//(the word we want to check and the word when it is coverted into capitalized word)
	 	{
			assertEquals(ExpectedLength, word.length());
	 	}
	 	//In a Csv(value = { , , , , , })  t default an empty value is treated as an null.   WE can fix that by adding " '  ' "(Single Quotes). Just add single quotes for that value on each side. {SingleQuote, SingleQuote} represents an "Empty Value". 
	 	
}



/*So, now let's set up a new project in eclipse saying the project name as "JUnit5".
- One of the most important things is typically we create "Source Code" in the source folder. So, that's where our usual code goes in.
- So, to store our Unit Test Code we would create a new folder, so we will create a "Java Source Folder". And we will call that as "test" and finish.
- Now, we create a new file in "test" saying "StringTest" by choosing the new Junit Jupiter test which is JUnit5.
- So, to run this we "Right-Click" in the JUnit test file and select Runas "Junit".

- Now, the important thing is we have a template of a test present here and another important thing is "name of the method" which can be anything we want but when we are writing test case for a particular method we name that test method by that name only.
- So, the important thing is it's annotated with "@Test". If we look at the code it's importing "org.junit.jupiter.api.Test" that's the annotation we are including in. Once we add an annotation @Test that means this is a test method. 
- When I run this test file with the test method this would run as a Junit test file.
- Since, we are saying fail("Not yet implemented"); the test is failing.

- How do we know which test is running? so there's a class called "TestInfo" if we add a parameter for the @BeforeEach methods, "TestInfo info" this is a useful tip whenever we are having problems with running tests and if there's an expception and we would want to findout before which test we are getting an exception. Then we can add "TestInfo info" in the test method parameter.
	
	ex: @BeforeEach
		void BeforeEach(TestInfo info){


		} 


*/
