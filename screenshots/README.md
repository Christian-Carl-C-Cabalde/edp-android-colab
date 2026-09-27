# Screenshots for Lab Activity 12 — LiceoFieldKit

These shots are **not** in the repo yet. They have to be captured on your own
emulator, because the grader checks the actual content of each image (a real
permission dialog, a real camera preview, your real coordinates). Save the seven
PNGs in this folder using exactly these names.

| File | What it must show |
|---|---|
| `01-camera-dialog.png` | The system camera permission dialog over your app |
| `02-rationale.png` | Your reason text and the "Try again" button |
| `03-open-settings.png` | The blocked message and the "Open Settings" button |
| `04-camera-photo.png` | Live preview, thumbnail and the "Saved:" line |
| `05-level.png` | Level card with numbers, and the Virtual sensors window beside the emulator |
| `06-location.png` | Coordinates, the ± accuracy and "Location access: Precise" |
| `07-branch.png` | Terminal showing `git branch` with `* lab-activity-12` and your last commit |
| `08-shake-capture.png` *(bonus)* | Logcat line "Shake capture" and the new thumbnail |
| `09-torch.png` *(optional)* | Real phone with the torch on and the button reading "Torch off" |

## How to walk the camera states in order

Shots 01 → 04 need one continuous run through all four permission states, so
capture them in this order on a fresh install.

```bash
adb shell pm clear edu.liceo.fieldkit   # back to a fresh install
```

1. Launch, tap **Allow Camera** → system dialog → `01-camera-dialog.png`
2. Tap **Don't allow** → reason + "Try again" → `02-rationale.png`
3. Tap **Try again**, then **Don't allow** again → "Open Settings" → `03-open-settings.png`
4. **Open Settings**, allow Camera, come back → preview appears, **Take photo** → `04-camera-photo.png`

## Other shots

- `05-level.png` — open ⋮ Extended controls → **Virtual sensors** beside the emulator,
  drag the phone until the card reads LEVEL ✓, then capture the whole window.
- `06-location.png` — Extended controls → **Location** → search "Cagayan de Oro" →
  **Set Location**. Tap **Tag my location**, choose **Precise**.
- `07-branch.png` — in Android Studio's Terminal:

  ```bash
  git branch
  git log -1 --oneline
  ```

  The `*` must be next to `lab-activity-12`.
- `08-shake-capture.png` — Logcat filtered on `FieldKit`, then a fast drag in
  Virtual sensors.
