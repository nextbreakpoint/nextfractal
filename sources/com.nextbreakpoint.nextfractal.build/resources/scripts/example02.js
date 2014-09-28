// Read example01.js before to read this example.
// This example shows how to perform some delayed operations.

context.println("I'll wait for 5 seconds...");

// The method sleep suspends the execution for a specified amount of time.
// If the user clicks on the visualization window before the specified 
// amount of time, the execution is resumed and the method returns true.
if (context.sleep(5000)) {
    context.println("The script has been interrupted by the user.");
} else {
    context.println("I have finished.");
}
