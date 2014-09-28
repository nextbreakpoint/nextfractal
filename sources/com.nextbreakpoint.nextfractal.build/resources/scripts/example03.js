// Read example02.js before to read this example.
// This example shows how to display a temporary message.

for (var i = 0; i < 5; i++) {
    // The method showMessage displays a temporary message on the visualization window.
    // The arguments are: the text to display, the font size, the horizontal position, 
    // the vertical position, the persistence time, the background visibility attribute.
    // In this example a new message is displayed for one second every two seconds.
    context.showMessage("Step " + i, 12, 5, 56, 1000, true);
    if (context.sleep(2000)) {
        // The script has been interrupted by the user.
        break;
    }
}
