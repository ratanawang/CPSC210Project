# Dark

### A game in which one escapes a maze—*in the dark*. 

> What is Dark about?

- Imagine that you've been trapped in a dark, dark room. Pitch black. You can hear a ticking noise. Somewhere in your heart, you know that you have to escape this room before the ticking noise runs out... 
- You'll just have to play the game if you want to know more. 

> Who can play Dark?

- Anyone who can type.

> What's the story behind the creation of Dark?

- Games are the perfect type of project because one can start small and keep building. I always think of new features halfway through the project, so this will allow me to implement those features as I see fit. 
- I just really like escape rooms, and thought that the "escape" and time limit elements would create the perfect kind of pressure for the player. As for why it's set in the dark, I thought it would be too easy for the player if they had a full view of the layout. Also it's scarier that way. 

### User Stories

- As a user, I want to be able to choose from the options presented in the main menu
- As a user, I want to be able to move in a specified direction (up/down/left/right)
- As a user, I want to be able to collect items, add them to my item pouch, and view them
- As a user, I want to be able to type in passwords whenever necessary

- As a user, I want to be able to save my progress (current location, collected items) for a level.
- As a user, I want to be able to play a level with previously saved progress. 

### Phase 4: Task 2

Sample Printed Event Log:
> Thu Mar 31 03:49:43 PDT 2022 <br>
> Key added to item pouch. <br>
> Thu Mar 31 03:49:46 PDT 2022 <br>
> Clue added to item pouch. <br>
> Thu Mar 31 03:49:50 PDT 2022 <br>
> Clue (47 days after Christmas [mm/dd].) was read. <br>
> Thu Mar 31 03:49:56 PDT 2022 <br>
> Clue (47 days after Christmas [mm/dd].) was read. <br>

*Note: The event log will not print if the user quits without playing a level.*

### Phase 4: Task 3

Refactoring:
- I would refactor the `Level`, `Level1`, and `KeyboardListener` classes so that they would belong to the `model` package instead, and transfer all UI capabilities to the `DarkGame` class.
- I would create an abstract way to create new levels, which would be simplified by making the following changes:
  - A `Level` would ideally have a random number of tiles (within a specified range) that would automatically and randomly join together upon instantiation of the level.
  - `Tile` would be the only thing that make up the structure of the maze, which would render the `MazeStructure` interface useless.
    - `Chest` would instead be an item (or something else?) that cannot be picked up.
    - `Exit` would become an inherent property of a single tile within the level.
  - To create new levels, one would instantiate a level with a single number (representing the level number) and a number of tiles, items, chests, and an exit would be created from a range that depends on the level number.
- I would get rid of the `ItemPouch` class, as I think that the `Player` class having its own list of `Items` would be less redundant.
- The UI would display player movements (e.g. via JLabel) as they happen, instead of only when hitting walls. 
- The UI would be dark-themed (i.e. black background, light-coloured text and buttons, etc.).
- Levels that are created from saved data would do so without triggering any `Event`; i.e. the `EventLog` would only show events that the user caused within the current session. 