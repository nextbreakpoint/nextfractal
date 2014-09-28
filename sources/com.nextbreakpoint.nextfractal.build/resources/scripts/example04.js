// Read example03.js before to read this example.
// This example shows how to use functions to reuse code.

// The variable running is used to save the current status.
var running = true;

show("Starting...");
if (context.sleep(2000)) {
    running = false;
}

if (running) {
    for (var i = 0; i < 5; i++) {
        show("Step " + i);
        if (context.sleep(2000)) {
            running = false;
            break;
        }
    }
}

// The variable running contains the final status.
show(running ? "Finished." : "Interrupted.");

// The function show contains some reusable code.
function show(message) {
    context.showMessage(message, 4, 5, 92, 1000, true);
}
