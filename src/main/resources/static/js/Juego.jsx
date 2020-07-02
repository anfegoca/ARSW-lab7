function Square(props) {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}
function BBServiceURL() {
    return 'ws://localhost:8080/bbService';
}
class Board extends React.Component {
    constructor(props) {
        super(props);
        
        this.comunicationWS = new WSBBChannel(BBServiceURL(),
            (msg) => {
                var obj = JSON.parse(msg);
                console.log("On func call back ", msg);
                this.handleClick(obj.value);
            }

        );
        this.state = {
            squares: Array(9).fill(null),
            xIsNext: true,
            wsreference: this.comunicationWS,
        };
        


    }

    handleClick(i) {
        console.log("HOLA "+i );
        this.state.wsreference.send(this.props.sala,i);
        
        const squares = this.state.squares.slice();
        squares[i] = this.state.xIsNext ? 'X' : 'O';
        this.setState({
            squares: squares,
            xIsNext: !this.state.xIsNext,
        });
    }

    renderSquare(i) {
        return (
            <Square
                value={this.state.squares[i]}
                onClick={() => this.handleClick(i)}
            />
        );
    }

    render() {
        const status = 'Next player: ' + (this.state.xIsNext ? 'X' : 'O');

        return (
            <div>
                <div className="status">{status}</div>
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
    render() {
        return (
            <div className="game">
                <div className="game-board">
                    <Board sala={this.props.sala}/>
                </div>
                <div className="game-info">
                    <div>{/* status */}</div>
                    <ol>{/* TODO */}</ol>
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
        console.log(this.state.visible);
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
                    <input type='submit' />
                </form>
                {"Sala:" + this.state.sala}
                {this.state.visible ? <Game sala={this.state.sala}/> : null}
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
    constructor(URL, callback) {
        this.URL = URL;
        this.wsocket = new WebSocket(URL);
        this.wsocket.onopen = (evt) => this.onOpen(evt);
        this.wsocket.onmessage = (evt) => this.onMessage(evt);
        this.wsocket.onerror = (evt) => this.onError(evt);
        this.receivef = callback;
    }
    onOpen(evt) {
        console.log("In onOpen", evt);
    }
    onMessage(evt) {
        console.log("In onMessage", evt);
        // Este if permite que el primer mensaje del servidor no se tenga en cuenta.
        // El primer mensaje solo confirma que se estableció la conexión.
        // De ahí en adelante intercambiaremos solo puntos(x,y) con el servidor
        if (evt.data != "Connection established.") {
            this.receivef(evt.data);
        }
    }
    onError(evt) {
        console.error("In onError", evt);
    }
    send(sala,i) {
        let msg = '{ "sala": ' + (sala) + ', "value": ' + (i) + "}";
        console.log("sending: ", msg);
        this.wsocket.send(msg);
    }
}