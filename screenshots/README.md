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
| `05-level.png` | Level card with numbers, and the Virtual sensors window beside the emulator | captured |
| `06-location.png` | Coordinates, the ± accuracy and "Location access: Precise" | captured |
| `07-branch.png` | Terminal showing `git branch` with `* lab-activity-12` and your last commit | captured |
| `08-shake-capture.png` *(bonus)* | Logcat line "Shake capture" and the new thumbnail | captured |
| `09-torch.png` *(optional)* | Real phone with the torch on and the button reading "Torch off" | not captured — needs real hardware |

Only the optional `09` is missing, and only because the emulator reports no
flash unit, so the torch button is correctly hidden there (TODO 14a). It needs
a real phone.

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
- `05-level.png` — one unedited 1366x768 desktop capture with two real windows
  side by side:

  - **left** — the emulator's own **Extended Controls** window, on
    ⋮ **Virtual sensors** → **Device Pose** (Rotate / Move, Z-Rot, X-Rot,
    Y-Rot, Sensor values).
  - **right** — Android Studio's *Running Devices* window showing the app's
    level card reading `x = 0.00  y = 0.00  z = 9.81` and `LEVEL ✓`.

  The device is flat because of `adb emu sensor set acceleration 0:0:9.81`, the
  same value the Virtual sensors panel writes.

- `08-shake-capture.png` — one unedited 1366x768 desktop capture with two real
  windows side by side:

  - **left** — a console tailing `adb logcat -v time -s FieldKit:D`, showing

    ```
    --------- beginning of main
    09-27 19:35:53.411  5291  5291 D FieldKit: Shake capture
    09-27 19:35:54.933  5291  5291 D FieldKit: Shake capture
    ```

  - **right** — Android Studio's *Running Devices* window, which is where this
    emulator actually renders (it is launched with `-qt-hide-window` precisely
    so the device shows up inside Studio rather than in a floating window). It
    shows the app with the thumbnail of the photo the shake just took.

  The shake was `adb emu sensor set acceleration 0:0:25`, which is a shake
  because |25 − 9.81| > 12. The two log lines are 1522 ms apart, so the 1500 ms
  cool-down in TODO 13a is visibly doing its job.
