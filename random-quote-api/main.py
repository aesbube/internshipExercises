import random
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import json

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

def get_quote():
    with open("quotes.json", "r", encoding="utf-8") as file:
        quotes = json.load(file)["quotes"]
    number = random.randint(0, len(quotes)-1)
    return quotes[number]

@app.get("/")
def read_root():
    return get_quote()