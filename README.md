# FloodFill-Puzzle

Idea for the puzzle and some sample inputs from reddit daily programmer: 
https://www.reddit.com/r/dailyprogrammer/comments/5jxeal/20161223_challenge_296_hard_flood_fill_puzzle_game/

Puzzle is this: given a board, an NxN grid of numbers with integer values between 1 and N inclusive. A move consistents of incrementing or decrementing one "group" on the board, consisting of one or more adjacent squares of the same value. The game is won when there is only one number remaining on the entire board.

I intend to allow a user to play the game, and I might add a move counter or some other fucntionality. I'd also like to make a program to find the/a fastest solution for a random board, but there is no obvious algorithm besides an unweighted shortest path algorithm on a graph of all possible moves, which strikes me as impractical. I'll have to think about how to program a solver.
