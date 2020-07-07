function Square(props) {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}
function BBServiceURL() {
    var host = window.location.host;
    var url = 'wss://' + (host) + '/bbService';
    console.log("URL Calculada: " + url);
    return url;
   }
class Board extends React.Component {


    renderSquare(i) {
        return (
            <Square
                value={this.props.squares[i]}
                onClick={() => this.props.onClick(i)}
            />
        );
    }


    render() {
        return (
            <div>
                <div className="board-row">
                    {this.renderSquare(0)}
                    {this.renderSquare(1)}
                    {this.renderSquare(2)}
                </div>
                <div className="board-row">
                    {this.renderSquare(3)}
                    {this.renderSquare(4)}
                    {this.renderSquare(5)}
                </div>
                <div className="board-row">
                    {this.renderSquare(6)}
                    {this.renderSquare(7)}
                    {this.renderSquare(8)}
                </div>
            </div>
        );
    }
}

class Game extends React.Component {
    constructor(props) {
        super(props);

        this.comunicationWS = new WSBBChannel(BBServiceURL(),
            (msg) => {
                if (msg[0] === "est") {
                    this.setState({ estado: msg[1] });
                } else if (msg[0] === "mov") {
                    this.jugar(msg[1]);
                }
                //var obj = JSON.parse(msg);
                //console.log("On func call back ", msg);
                //this.handleClick(obj.value);
            }

            , this.props.sala, this.props.username);
        this.state = {
            estado: " ",
            squares: Array(9).fill(null),
            wsreference: this.comunicationWS,
            history: [],
            stepNumber: 0,
        };



    }

    jumpTo(move) {
        this.state.wsreference.send("His", this.props.sala, this.props.username, move);
    }
    jugar(movs) {
        var history = [];
        const squares2 = this.state.squares.slice();
        let temp = movs.split(",");
        var cont=0;
        for (var i = 0; i < 9; i++) {
            if (temp[i] === "X" || temp[i] === "O") {
                squares2[i] = temp[i];
                history=history.concat(cont);
                cont++;
            }else{
                squares2[i]=null;
            }
        }

        //squares[i] = x;
        this.setState({
            history: history,
            stepNumber: history.length,
            squares: squares2,
        });
        //this.setState({
        //   squares: squares,
        //});
    }
    handleClick(i) {
        this.state.wsreference.send("Mov", this.props.sala, this.props.username, i);
    }
    render() {
        const status = this.state.estado;
        const history = this.state.history;
        const moves = history.map((step, move) => {
            const desc = move ?
              'Go to move #' + move :
              'Go to game start';
            return (
              <li key={move}>
                <button onClick={() => this.jumpTo(move)}>{desc}</button>
              </li>
            );
        });
        return (
            <div className="game">
                <div className="game-board">
                    <Board
                        squares={this.state.squares}
                        onClick={i => this.handleClick(i)}
                    />
                </div>
                <div className="game-info">
                    <div>{status}</div>
                    <ol>{moves}</ol>
                </div>
            </div>
        );
    }
}
class MyForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            estado: "Ingrese su nombre y número de la sala, si no existe la sala, se creará",
            username: '',
            sala: null,
            visible: false,
        };
    }
    mySubmitHandler = (event) => {
        event.preventDefault();
        let sala = this.state.sala;
        if (!Number(sala)) {
            alert("El código debe ser númerico");
        } else {
            this.setState({ visible: true });
        }

    }
    myChangeHandler = (event) => {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({ [nam]: val });
    }
    render() {
        return (
            <div>
                <form onSubmit={this.mySubmitHandler} >
                    {this.state.estado}
                    <p>Nombre:</p>
                    <input
                        type='text'
                        name='username'
                        onChange={this.myChangeHandler}
                    />
                    <p>Sala:</p>
                    <input
                        type='text'
                        name='sala'
                        onChange={this.myChangeHandler}
                    />
                    <br />
                    <br />
                    {!this.state.visible ? <input type='submit' /> : null}
                </form>
                {"Sala:" + this.state.sala}
                {this.state.visible ? <Game sala={this.state.sala} username={this.state.username} /> : null}
            </div>
        );
    }
}

ReactDOM.render(

    <MyForm></MyForm>,
    document.getElementById('root')
);

// Retorna la url del servicio. Es una función de configuración.
function BBServiceURL() {
    return 'ws://localhost:8080/bbService';
}
class WSBBChannel {
    constructor(URL, callback, sala, username) {
        this.URL = URL;
        this.wsocket = new WebSocket(URL);
        this.wsocket.onopen = (evt) => this.onOpen(evt);
        this.wsocket.onmessage = (evt) => this.onMessage(evt);
        this.wsocket.onerror = (evt) => this.onError(evt);
        this.receivef = callback;
        this.sala = sala;
        this.username = username;
    }
    onOpen(evt) {
        console.log("In onOpen", evt);
        this.send("Sala", this.sala, this.username, "");
    }
    onMessage(evt) {
        console.log("In onMessage", evt);
        // Este if permite que el primer mensaje del servidor no se tenga en cuenta.
        // El primer mensaje solo confirma que se estableció la conexión.
        // De ahí en adelante intercambiaremos solo puntos(x,y) con el servidor
        if (evt.data != "Connection established.") {
            let mens = evt.data.split("/");
            this.receivef(mens);
        }
    }
    onError(evt) {
        console.error("In onError", evt);
    }
    send(text, num, jug, mov) {
        let msg = (text) + " " + (num) + " " + (jug) + " " + (mov);
        console.log("sending: ", msg);
        this.wsocket.send(msg);
    }
}