const apiBaseUrl = "http://localhost:8080";

async function startGame() {
    try {
        const response = await fetch(`${apiBaseUrl}/start`);
        const result = await response.text();
        console.log("Start Game Response:", result);
        document.getElementById("game-status").innerText = result;
    } catch (error) {
        console.error("Error in startGame:", error);
    }
}
async function drawCard() {
    try {
        const response = await fetch(`${apiBaseUrl}/draw`);
        const result = await response.text();
        console.log("Draw Card Response:", result);
        document.getElementById("game-status").innerText = result;
    } catch (error) {
        console.error("Error in drawCard:", error);
    }
}