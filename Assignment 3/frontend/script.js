let isGameRunning = false;
let lastOutput = "";

async function startGame() {
    try {
        const response = await fetch('http://localhost:8080/start');
        if (response.ok) {
            document.getElementById('game-output').innerHTML = ''; // Clear previous game
            isGameRunning = true;
            pollOutput();
            pollGameStatus();
        }
    } catch (error) {
        console.error("Error starting game:", error);
    }
}

async function test0WinnerQuest() {
    try {
        const response = await fetch('http://localhost:8080/0_winner_quest');
        if (response.ok) {
            document.getElementById('game-output').innerHTML = ''; // Clear previous game
            isGameRunning = true;
            pollOutput();
            pollGameStatus();
        }
    } catch (error) {
        console.error("Error starting test scenario:", error);
    }
}

async function test2winner_game_2winner_quest() {
    try {
        const response = await fetch('http://localhost:8080/2winner_game_2winner_quest');
        if (response.ok) {
            document.getElementById('game-output').innerHTML = ''; // Clear previous game
            isGameRunning = true;
            pollOutput();
            pollGameStatus();
        }
    } catch (error) {
        console.error("Error starting test scenario:", error);
    }
}


async function testA1Scenario() {
    try {
        const response = await fetch('http://localhost:8080/A1_scenario');
        if (response.ok) {
            document.getElementById('game-output').innerHTML = ''; // Clear previous game
            isGameRunning = true;
            pollOutput();
            pollGameStatus();
        }
    } catch (error) {
        console.error("Error starting test scenario:", error);
    }
}
async function test1winner_game_with_events() {
    try {
        const response = await fetch('http://localhost:8080/1winner_game_with_events');
        if (response.ok) {
            document.getElementById('game-output').innerHTML = ''; // Clear previous game
            isGameRunning = true;
            pollOutput();
            pollGameStatus();
        }
    } catch (error) {
        console.error("Error starting test scenario:", error);
    }
}

async function pollOutput() {
    while (isGameRunning) {
        try {
            const response = await fetch('http://localhost:8080/getOutput');
            const output = await response.text();

            if (output && output !== lastOutput) {
                const gameOutput = document.getElementById('game-output');
                gameOutput.innerHTML += output.split('\n').join('<br>') + '<br>';
                gameOutput.scrollTop = gameOutput.scrollHeight;
                lastOutput = output;
            }

            await new Promise(resolve => setTimeout(resolve, 100));
        } catch (error) {
            console.error('Error polling output:', error);
            isGameRunning = false;
        }
    }
}

async function sendInput() {
    const input = document.getElementById('userInput').value;
    document.getElementById('userInput').value = '';

    try {
        const response = await fetch('http://localhost:8080/sendInput', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `input=${encodeURIComponent(input)}`
        });

        if (!response.ok) {
            console.error('Failed to send input:', await response.text());
        }
    } catch (error) {
        console.error("Error sending input:", error);
    }
}
async function showCurrentHands() {
    try {
        const response = await fetch('http://localhost:8080/getCurrentHands');
        const hands = await response.json();

        document.getElementById('p1-hand').textContent = hands[0];
        document.getElementById('p2-hand').textContent = hands[1];
        document.getElementById('p3-hand').textContent = hands[2];
        document.getElementById('p4-hand').textContent = hands[3];
    } catch (error) {
        console.error('Error getting current hands:', error);
    }
}

function showHandsButton() {
    document.getElementById('show-hands-button').style.display = 'block';
}


async function updateGameStatus() {
    try {
        const response = await fetch('http://localhost:8080/getGameStatus');
        const status = await response.json();


        const p1Status = document.getElementById('p1-status');
        const p2Status = document.getElementById('p2-status');
        const p3Status = document.getElementById('p3-status');
        const p4Status = document.getElementById('p4-status');

        p1Status.textContent = status[0];
        p2Status.textContent = status[1];
        p3Status.textContent = status[2];
        p4Status.textContent = status[3];


    } catch (error) {
        console.error('Error updating game status:', error);
    }
}

async function pollGameStatus() {
    while (isGameRunning) {
        await updateGameStatus();
        await new Promise(resolve => setTimeout(resolve, 1000));
    }
}

function handleKeyPress(event) {
    if (event.key === 'Enter') {
        sendInput();
    }
}


