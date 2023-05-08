# MasterMind_SAE126

# Manuel LEITAO, Lucas LAURET, Quentin SAUNER, Lucas FULCRAND, Johan LANCON

# Goal of the project

The goal of the "HMI development" project is to build a board game type application using the Java language and the
JavaFx graphic library. From a pedagogical point of view, this project makes it possible to put into practice the
knowledge covered during the courses, with in particular the paradigm of MVC architecture (Model - View - Controller),
object programming, unit testing, mathematics, etc. This project being transversal to the training, the expectations
also bring into play subjects such as expression, English and economics-law.

In order to guarantee that the objectives and expected deliverables are respected, the subject is not free. The possible
games are described in the next section. Their implementation will necessarily be done using game development
frameworks, provided by the department, which come in two versions: one for games in text mode, and another for games in
graphic mode using JavaFX. This framework makes it possible to respect a strict MVC architecture, and facilitates the
implementation of unit tests. In addition, it provides most of the "mechanics" needed to create a board game, which
allows the developer to focus on coding the rules of the chosen game, its visual representation and the decision
algorithms to allow the computer to play.

In this regard, each project must allow play either with 2 human or computer players, or with 1 human and 1 computer.
Moreover, when a player is the computer, he must be able to apply at least 2 different strategies to make his game
decisions. More details are indicated in the specifications section.

<p align="center">
    <img style="border:3px solid black" src="https://user-images.githubusercontent.com/114138178/235857664-04b06e80-c5f8-4440-9cdc-5968be7a4467.png"/>
</p>
<span style="color:grey"> Mastermind is a classic two-player code-breaking board game that was invented in 1970 by Mordecai Meirowitz. The game is
played on a board with a code-maker, who chooses a sequence of colored pegs (or other symbols) and hides them behind a
screen. The code-breaker then tries to guess the sequence by placing pegs on the board in a particular order, and the
code-maker provides feedback on the guess by placing white or black pegs on the board. </span>

<span style="color:grey">*A white peg indicates that a guessed peg is the right color but in the wrong position, while a
black peg indicates that
a guessed peg is the right color and in the correct position. The code-breaker has a limited number of turns to guess
the sequence, and the game can be played with different numbers of pegs and colors to make it more challenging. The game
is won if the code-breaker correctly guesses the sequence before running out of turns, and lost otherwise. Mastermind is
a popular game for all ages and has been adapted into many electronic versions and mobile apps. </span>