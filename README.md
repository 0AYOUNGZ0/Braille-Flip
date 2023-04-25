[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=102)](https://opensource.org/licenses/MIT)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/webianks/BlueChat/blob/master/LICENSE)

# Braille Flip

**BrailleFlip** is a low-budget, hybrid system designed for mobile Braille input and editing. In this project, we attach a secondary phone to the back of the primary phone using hook-and-loop fasteners and utilize the screen areas of the secondary phone as a touchpad for Braille code input. The screen of the primary phone is used for Braille editing, such as selecting, copying, and pasting, and quick actions like copying all text and clear all text. To activate edit mode on the primary phone, the user can perform a long press on the "EDIT" button. In this mode, the user can utilize various gestures to perform text-editing operations.

## Screenshots

> _Add your screenshots here._

## Project Structure

```
.
├── AndroidManifest.xml
└── java
    └── com
        └── university
            └── brailleflip
                ├── App.java
                ├── MainActivity.java
                ├── SendActivity.java
                ├── ShowActivity.java
                ├── adapter
                │   └── MyBlueToothAdapter.java
                ├── bean
                │   └── BlueToothDevicesInfo.java
                ├── thread
                │   ├── AcceptThread.java
                │   ├── ConnectThread.java
                │   ├── ConnectedMsgThread.java
                │   └── ThreadManager.java
                ├── ui
                │   └── GestureUI.java
                └── utils
                    ├── ProgressDialogUtil.java
                    ├── RecognitionUtils.java
                    └── Utils.java
└── res
    ├── drawable
    │   ├── ic_launcher_background.xml
    │   └── progress_bg.xml
    ├── drawable-v24
    │   └── ic_launcher_foreground.xml
    ├── layout
    │   ├── activity_main.xml
    │   ├── activity_send.xml
    │   ├── activity_show.xml
    │   ├── custom_progress_dialog_view.xml
    │   └── item_bluetooth.xml
    ├── mipmap-*dpi
    │   ├── ic_launcher.webp
    │   └── ic_launcher_round.webp
    ├── mipmap-anydpi-v26
    │   ├── ic_launcher.xml
    │   └── ic_launcher_round.xml
    ├── mipmap-anydpi-v33
    │   └── ic_launcher.xml
    ├── values
    │   ├── colors.xml
    │   ├── strings.xml
    │   └── themes.xml
    ├── values-night
    │   └── themes.xml
    └── xml
        ├── backup_rules.xml
        └── data_extraction_rules.xml
```

```
## MIT License

Copyright (c) 2017 Ramankit Singh

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
