

import struct
import numpy as np
import matplotlib.pyplot as plt
from scipy.fftpack import fft
import time
from tkinter import TclError
from tstaudio import AudioStream
audio = AudioStream


# create matplotlib axes
fig, (ax, ax2) = plt.subplots(2, figsize=(15, 8))


# variable for plotting
x = np.arange(0, 2 * audio.CHUNK, 2)
x_fft = np.linspace(0, audio.RATE, audio.CHUNK)

# create a line object with random data
line, = ax.plot(x, np.random.rand(audio.CHUNK), '-', lw=2)
# semilogx line for spectrum
line_fft, = ax2.semilogx(x_fft, np.random.rand(audio.CHUNK), '-', lw=2)

# basic formatting axes
ax.set_title('AUDIOWAVE')
ax.set_xlabel('samples')
ax.set_ylabel('volume')
ax.set_ylim(0, 255)
ax.set_xlim(0, 2 * audio.CHUNK)
plt.setp(ax, xticks=[0, audio.CHUNK, 2 * audio.CHUNK], yticks=[0, 128, 255])

ax2.set_xlim(20, audio.RATE / 2)

# show the plot
plt.show(block=False)

print('stream started')

# for measuring frame rate
frame_count = 1
start_time = time.time()

while True: #by authoy mjay for the spectrum
    # binary data
    data = audio.stream.read(audio.CHUNK)

    # convert data to integers, make np array, then offset it by 127
    data_int = struct.unpack(str(2 * audio.CHUNK) + 'B', data)

    # create np array and offset by 128
    data_np = np.array(data_int, dtype='b')[::2] + 128

    line.set_ydata(data_np)

    # compute FFT and update line
    yf = fft(data_int)
    line_fft.set_ydata(np.abs(yf[0:audio.CHUNK]) / (128 * audio.CHUNK))

    # update figure canvas
    try:
        fig.canvas.draw()
        fig.canvas.flush_events()
        frame_count += 1

    except TclError:
        # calculate average frame rate
        frame_rate = frame_count / (time.time() - start_time)

        print('end of stream')
        print('average frame rate = {:.0f} FPS'.format(frame_rate))
        break
