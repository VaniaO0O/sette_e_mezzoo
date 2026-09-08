# Sette e Mezzo

### University Group Project

A Java desktop application that simulates the Italian card game **Sette e Mezzo**, featuring a graphical user interface, computer-controlled opponents with different difficulty levels, game persistence, and multiple software design patterns.

---

## Overview

The application simulates a game of **Sette e Mezzo** between one human player and three computer-controlled players.

Each player starts with a configurable number of tokens and takes part in successive rounds. Players can:

* Place a bet;
* Draw additional cards;
* Pass and end their turn;
* Win or lose tokens depending on the outcome of the round.

The computer-controlled players use different decision-making strategies depending on the selected difficulty level.

The application also supports **saving and loading game states**, allowing a game to be resumed at a later time.

---

## Technologies

* **Java 8**
* **Java Swing**
* **Object-Oriented Programming**
* **Multithreading**
* **Java Serialization**
* **Factory Pattern**
* **Observer Pattern**
* **Memento Pattern**
* **Strategy Pattern**
* **Singleton Pattern**

---

## Gameplay

The game consists of a human player competing against three CPU players.

Each turn is composed of three possible actions:

### Punta

The player places a bet using their available tokens.

The amount of the bet is selected by the human player, while CPU players calculate their bet automatically according to their strategy.

### Pesca

The player draws another card from the deck.

The action can be repeated as long as the player remains in the game.

### Passa

The player ends their turn and passes control to the next player.

The round ends when all players have completed their turns, after which the winner is calculated and the corresponding tokens are updated.

---

## CPU Strategies

The application implements two different strategies for computer-controlled players.

### Normal Difficulty

The normal CPU determines its bet using the average of the bets placed by the previous players, applying a random factor between **-20% and +20%**.

For its actions:

* It draws cards while its score is below **5.5**;
* It passes once its score reaches or exceeds **5.5**.

### Difficult Difficulty

The difficult CPU uses the current hand score to determine its bet.

Its bet is based on twice its current card score, with:

* A minimum bet of **3 tokens**;
* A maximum equal to the number of tokens owned.

Its decision to draw or pass is based on the distance between its current score and **7.5**:

* If the distance is greater than 2, it draws;
* If the distance is between 1 and 2, it has a 50% chance of drawing;
* Otherwise, it passes.

The two behaviours are implemented through the **Strategy Pattern**, allowing the CPU decision-making algorithm to be changed without modifying the game management logic.

---

## Graphical User Interface

The application features a graphical interface developed using **Java Swing**.

The interface is organized into independent screens through the `Screen` interface, allowing the main window to dynamically switch between different views.

The GUI includes:

* Interactive game screens;
* Controls for player actions;
* Dialogs for betting and game notifications;
* File chooser for saving and loading games;
* Custom graphical assets and application icon;
* Dynamic updates based on the current game state;
* Visual highlighting of the player whose turn it is.

The application also uses a separate thread for CPU turns, allowing the computer players to perform their actions asynchronously while introducing a short delay to simulate decision-making.

---

## Design Patterns

The project applies several software design patterns to separate responsibilities and make the application easier to manage and extend.

### Factory Pattern

The `MazzoFactory` interface defines the operations required to manage a deck of cards.

`MazzoNapoletano` provides the concrete implementation for the Neapolitan deck and handles operations such as:

* Drawing cards;
* Resetting the deck;
* Shuffling;
* Replacing the current cards;
* Retrieving the remaining cards.

This abstraction allows the deck implementation to be separated from the rest of the game logic.

### Singleton Pattern

`MazzoNapoletano` is implemented as a Singleton.

The `getInstance()` method ensures that a single shared instance of the deck is used throughout the game.

This allows all game components to operate on the same current deck.

### Observer Pattern

The Observer Pattern is used to notify components when the current turn changes.

The pattern is implemented through:

* `TurnObserver`
* `TurnSubject`
* `PlayerArea`

When the current player changes, observers are notified and the corresponding `PlayerArea` updates its graphical state.

The current player is visually highlighted in the interface, providing immediate feedback to the user.

### Memento Pattern

The Memento Pattern is used to save and restore the state of a game.

The main components are:

* `GameOriginator`, which manages the current game state;
* `GameMemento`, which stores a snapshot of the game;
* `GameData`, which represents the stored state;
* `Caretaker`, which manages saved mementos.

The saved state includes information such as:

* Players;
* Players' tokens;
* Current deck;
* Selected CPU difficulty.

The memento can also be serialized to a file, allowing the game to be saved and subsequently loaded.

### Strategy Pattern

The Strategy Pattern encapsulates the decision-making algorithms used by CPU players.

The `ActionStrategy` interface defines the operations required by a CPU strategy:

* Determine the number of tokens to bet;
* Determine whether to draw or pass.

Two concrete implementations are provided:

* `CPU`
* `CPUDifficile`

`GameManager` can select the appropriate strategy at runtime without depending on the implementation details of the CPU behaviour.

---

## Save & Load

The application supports persistent game states.

At the end of a round, the current state can be saved to a file through the application's file chooser.

The saved data includes the information required to restore the game, including:

* Players and their tokens;
* Current deck;
* Selected game mode.

Java serialization is used together with the **Memento Pattern** to store and restore the game state.

---

## Multithreading

CPU turns are executed using separate Java threads.

`GameManager` creates a dedicated thread for CPU actions and uses synchronization through a shared `cpuLock` object to prevent concurrent CPU operations from interfering with the game state.

A short delay is also introduced before CPU decisions to simulate the computer "thinking" before performing an action.

---

## Application Architecture

The application separates the main responsibilities of the game into different components.

The `GameManager` acts as the central coordinator between:

* Players;
* Deck management;
* CPU strategies;
* Turn management;
* GUI updates;
* Game persistence.

The use of interfaces and design patterns allows different parts of the application to interact without being tightly coupled to specific implementations.

---

## Main Concepts

This project provided practical experience with:

* Object-oriented programming in Java;
* Interface-based design;
* Java Swing GUI development;
* Event-driven programming;
* Software design patterns;
* Runtime strategy selection;
* State persistence and serialization;
* Multithreading;
* Thread synchronization;
* Collection management;
* Exception handling;
* Separation of concerns.

---

## Authors

University group project developed as part of a Computer Science course.

**Authors:**

* Vania Orlacchio
* Lorenzo Menna
