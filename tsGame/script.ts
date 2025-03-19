const board = document.getElementById('board') as HTMLDivElement;
const restartButton = document.getElementById('restartButton') as HTMLButtonElement;
const undoButton = document.getElementById('undoButton') as HTMLButtonElement;
const layout = document.getElementById('game') as HTMLDivElement;
const winner = document.getElementById('winner') as HTMLHeadingElement;
const turn = document.getElementById('status') as HTMLHeadingElement;

class Game {
    board: string[];
    currentPlayer: string;
    winner: string;
    moves: number;
    history: string[][];

    constructor() {
        this.board = Array(9).fill('');
        this.currentPlayer = 'X';
        this.winner = '';
        this.moves = 0;
        this.history = [];
        console.log(this.history);
        turn.textContent = `${this.currentPlayer}`;
    }

    playMove(cell: number) {
        if (this.board[cell] === '' && this.winner === '') {
            this.board[cell] = this.currentPlayer;
            console.log(this.currentPlayer)
            this.moves++;
            this.history.push([...this.board]);
            this.checkWinner();
            this.currentPlayer = this.currentPlayer === 'X' ? 'O' : 'X';
        }
    }

    checkWinner() {
        for (const [a, b, c] of winCombinations) {
            if (this.board[a] && this.board[a] === this.board[b] && this.board[a] === this.board[c]) {
                this.winner = this.currentPlayer;
            }
        }
        return false;
    }

    undoMove() {
        if (this.moves > 0) {
            console.log("before", this.history);
            this.history.pop();
            if (this.history.length > 0) {
                this.board = [...this.history[this.history.length - 1]];
            } else {
                this.board = Array(9).fill('');
            }
            
            console.log("after", this.history);
            this.currentPlayer = this.currentPlayer === 'X' ? 'O' : 'X';
            this.moves--;
            this.winner = '';
        }
    }
}

const winCombinations = [
    [0, 1, 2], [3, 4, 5], [6, 7, 8],
    [0, 3, 6], [1, 4, 7], [2, 5, 8],
    [0, 4, 8], [2, 4, 6]
];

function createBoard() {
    board.innerHTML = '';
    for (let i = 0; i < 9; i++) {
        const cell = document.createElement('div');
        cell.classList.add('cell');
        cell.id = i.toString();
        cell.addEventListener('click', handleClick);
        board.appendChild(cell);
    }
    restartButton.addEventListener('click', handleRestart);
    undoButton.addEventListener('click', handleUndo);
}

function handleRestart(this: HTMLButtonElement, ev: MouseEvent) {
    board.style.pointerEvents = 'auto';
    winner.textContent = '';
    console.log('restart clicked');
    game = new Game();
    createBoard();
}

function handleClick(this: HTMLDivElement, ev: MouseEvent) {
    console.log('cell clicked');
    console.log(game.history);
    const cell = this as HTMLDivElement;
    cell.textContent = game.currentPlayer;
    game.playMove(parseInt(cell.id));
    if (game.winner !== ''  ) {
        winner.textContent = `${game.winner} wins!`;
        board.removeEventListener('click', handleClick);
        undoButton.removeEventListener('click', handleUndo);
        board.style.pointerEvents = 'none';
    } else {
        turn.textContent = `${game.currentPlayer}`;
    }
}

function handleUndo(this: HTMLButtonElement, ev: MouseEvent) {
    console.log('undo clicked');
    game.undoMove();
    turn.textContent = `${game.currentPlayer}`;
    console.log(game.board);
    const cells = document.querySelectorAll('.cell');
    cells.forEach((cell, i) => {
        cell.textContent = game.board[i];
    }); 
}


let game = new Game();
createBoard();



