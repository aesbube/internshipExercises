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


@app.get("/api/heroes")
def get_heroes():
    with open("heroes.json", "r") as file:
        data = json.load(file)
    return data["heroes"]


@app.get("/api/heroes/search?query={query}")
def get_heroes_search(query: str):
    with open("heroes.json", "r") as file:
        data = json.load(file)
    heroes = data["heroes"]
    heroes_result = [hero for hero in heroes if query.lower() in hero["name"].lower()]
    return heroes_result


@app.get("/api/hero-detail/{id}")
def get_heroes_id(id: int):
    with open("heroes.json", "r") as file:
        data = json.load(file)
    heroes = data["heroes"]
    hero = next((hero for hero in heroes if hero["id"] == id), None)
    return hero

