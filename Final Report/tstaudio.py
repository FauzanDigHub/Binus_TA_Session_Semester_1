import  pyaudio
class AudioStream(object):
    def __init__(self):

        # stream constants
        self.CHUNK = 1024 * 2
        self.FORMAT = pyaudio.paInt16
        self.CHANNELS = 1
        self.RATE = 44100
        self.pause = False

        # stream object
        self.p = pyaudio.PyAudio()
        self.stream = self.p.open(
            format=self.FORMAT,
            channels=self.CHANNELS,
            rate=self.RATE,
            input=True,
            output=True,
            frames_per_buffer=self.CHUNK,
        )

    # Base
    CHUNK = 1024 * 2  # samples per frame
    FORMAT = pyaudio.paInt16  # audio format ( 2 bytes per sample)
    CHANNELS = 1  # single channel for microphone
    RATE = 44100  # samples per second

    # pyaudio class instance
    p = pyaudio.PyAudio()

    # stream object to get data from microphone
    stream = p.open(
        format=FORMAT,
        channels=CHANNELS,
        rate=RATE,
        input=True,
        output=True,
        frames_per_buffer=CHUNK
    )