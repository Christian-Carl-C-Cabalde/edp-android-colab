# Implementation Plan - Profile Screen Build Tasks

This plan covers the implementation of a profile screen including theme definition, layout construction, and styling.

## User Review Required

> [!IMPORTANT]
> The "six light and six dark colors" mentioned in the prompt were not found in the provided context. I will use placeholder Material 3 colors for now. Please provide the specific hex codes if you want me to use exact ones.

## Open Questions

1. What are the specific hex codes for the "six light and six dark colors" mentioned?
2. Are there specific icons and text for the "stats" (Region E) and "info rows" (Region F)?
3. For the Floating Action Button (Region G), what icon and action should it have?

## Proposed Changes

### Theme Definition

#### [MODIFY] [Color.kt](file:///D:/edp-android-colab/app/src/main/java/com/example/myapplication/ui/theme/Color.kt)
- Define the 12 color constants (6 for light, 6 for dark).

#### [MODIFY] [Theme.kt](file:///D:/edp-android-colab/app/src/main/java/com/example/myapplication/ui/theme/Theme.kt)
- Update `lightColorScheme` and `darkColorScheme` with the new colors.
- Rename or add `ProfileTheme` composable as requested.
- (Stretch Goal) Add dynamic color branch.

### Layout Construction

#### [MODIFY] [MainActivity.kt](file:///D:/edp-android-colab/app/src/main/java/com/example/myapplication/MainActivity.kt)
- Implement `ProfileScreen` composable.
- Use `Scaffold` with `TopAppBar` and `FloatingActionButton`.
- Implement Regions B (Avatar + Dot), C (Name + Role), D (Buttons), E (Stats Card), F (Info Card).
- Ensure all colors come from `MaterialTheme.colorScheme`.

### Verification Plan

#### Automated Tests
- Run `./gradlew app:assembleDebug` to ensure it builds.

#### Manual Verification
- Add `@Preview` for Light mode.
- Add `@Preview` for Dark mode.
- Use `render_compose_preview` to verify the UI visually if possible.
- Verify text readability in both modes.

## Stretch Goals Implementation
- Coil `AsyncImage` for avatar.
- Stateful stats and "Follow" toggle.
- Theme toggle in `TopAppBar`.
