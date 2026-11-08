# Alternating Row Colors — Jetpack Compose

**Student:** Medikhanov Arman  
**Student ID:** 1712

## Objective

Display words as rows of letter boxes. Odd-numbered rows use the theme's standard primary color. Even-numbered rows have a grey background, with several randomly selected yellow letter boxes.

## Implementation

- `Greeting()` in `app/src/main/java/kg/iuca/campuscard/Greeting.kt` creates a list of words and displays them using a `Column`.
- Every word is drawn by the extracted `LetterRow()` composable using a `Row` of individual `Box` elements.
- Because list indexes start at zero, `rowNumber = index + 1` makes the visible first row row 1. `rowNumber % 2 == 0` identifies even-numbered rows.
- Odd rows use `MaterialTheme.colorScheme.primary`. Even rows use `Color.LightGray`.
- In every even row, `word.indices.shuffled().take(3).toSet()` selects three different letter positions. Those letter boxes use `Color.Yellow`.
- `remember(rowNumber, word)` keeps the randomly selected positions stable across ordinary recompositions.
- `MainActivity` now launches `Greeting()`; the previous `CampusCardScreen()` and its exercises are preserved for reference.

## How to check

1. Open the project in Android Studio.
2. Switch to branch `feature/alternating-row-colors`, then run on an Android emulator.
3. Confirm rows 1, 3 and 5 are primary-colored.
4. Confirm rows 2, 4 and 6 are grey and each contains three yellow letter boxes.
5. Optionally restart the app and observe that the random positions can change.

## Screenshot

The emulator screenshot is included in this branch: [alternating-rows-screenshot.png](alternating-rows-screenshot.png). It shows the completed alternating-row layout for submission.
