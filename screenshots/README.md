# Screenshots for Lab Activity 12 — LiceoFieldKit

All shots below were captured from the real app running on the
`Resizable_Experimental` emulator (Android 17 / API 37, Google APIs image,
1080x2400). Names match the lab sheet exactly.

| File | What it must show | Status |
|---|---|---|
| `01-camera-dialog.png` | The system camera permission dialog over your app | captured |
| `02-rationale.png` | Your reason text and the "Try again" button | captured |
| `03-open-settings.png` | The blocked message and the "Open Settings" button | captured |
| `04-camera-photo.png` | Live preview, thumbnail and the "Saved:" line | captured |
| `05-level.png` | Level card with numbers, and the Virtual sensors window beside the emulator | **partial** — see below |
| `06-location.png` | Coordinates, the ± accuracy and "Location access: Precise" | captured |
| `07-branch.png` | Terminal showing `git branch` with `* lab-activity-12` and your last commit | captured |
| `08-shake-capture.png` *(bonus)* | Logcat line "Shake capture" and the new thumbnail | **partial** — see below |
| `09-torch.png` *(optional)* | Real phone with the torch on and the button reading "Torch off" | not captured — needs real hardware |

## Two shots need one manual step on your side

`05-level.png` and `08-shake-capture.png` were captured from the device only,
because the emulator on this machine is started with `-qt-hide-window`
(`emulator.exe ... -qt-hide-window`). There is no emulator window on the
desktop, so the emulator's **Extended controls → Virtual sensors** panel cannot
be shown next to it, and a Logcat panel cannot be shown next to the thumbnail.

What *is* already in those two files, captured for real:

- `05-level.png` — the level card with live numbers, driven to a flat pose
  through the emulator's virtual sensor (`adb emu sensor set acceleration 0:0:9.81`,
  the same control the Virtual sensors panel exposes), showing
  `x = 0.00  y = 0.00  z = 9.81` and `LEVEL ✓`.
- `08-shake-capture.png` — the thumbnail of the photo taken by the shake
  trigger. The matching logcat line was produced at the same moment:

  ```
  09-27 19:15:54.388  5291  5291 D FieldKit: Shake capture
  09-27 19:15:55.927  5291  5291 D FieldKit: Shake capture
  ```

To finish either one, start the emulator **with a window** (in Android Studio,
or `emulator -avd Resizable_Experimental` without `-qt-hide-window`), open
⋮ Extended controls → Virtual sensors, and recapture.

## How the four camera states were walked

Shots 01 → 04 are one continuous run through all four permission states.

```bash
adb shell pm clear edu.liceo.fieldkit   # back to a fresh install
```

1. Launch, tap **Allow Camera** → system dialog → `01-camera-dialog.png`
2. Tap **Don't allow** → reason + "Try again" → `02-rationale.png`
3. Tap **Try again**, then **Don't allow** again → "Open Settings" → `03-open-settings.png`
4. **Open Settings** → Permissions → Camera → *Allow only while using the app*,
   then back to the app → preview appears, **Take photo** → `04-camera-photo.png`

The real Settings round trip in step 4 is what proves `LifecycleResumeEffect`
(TODO 3b) re-checks the permission: the preview binds on return without a
restart.

## Other shots

- `06-location.png` — `adb emu geo fix 124.0639 8.4802` (Cagayan de Oro), then
  **Tag my location** and choose **Precise** in the dialog.
- `07-branch.png` — a console window in the repo root running
  `git branch -vv`, `git log --oneline -3` and `git status -sb`.
- `08-shake-capture.png` — `adb emu sensor set acceleration 0:0:25` is a shake
  (|25 − 9.81| > 12); the two log lines above are 1539 ms apart, which is the
  1500 ms cool-down in TODO 13a working.
