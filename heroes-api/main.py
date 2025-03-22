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


@app.get("/api/heroes/search")
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


@app.get("/api/sort")
def get_sort_heroes(query: str):
    with open("heroes.json", "r") as file:
        data = json.load(file)
    heroes = data["heroes"]
    if query == "asc":
        heroes.sort(key=lambda x: x["name"], reverse=True)
    elif query == "desc":
        heroes.sort(key=lambda x: x["name"], reverse=False)
    return heroes


@app.get("/api/heroes/top")
def get_top_n_heroes(n: int):
    with open("heroes.json", "r") as file:
        data = json.load(file)
    heroes = data["heroes"]
    heroes.sort(key=lambda x: x["name"], reverse=False)
    return heroes[:n]


@app.post("/api/heroes")
def save_hero(name: str):
    with open("heroes.json", "r") as file:
        data = json.load(file)
    heroes = data["heroes"]
    hero = {
        "id": len(heroes) + 1,
        "name": name
    }
    heroes.append(hero)
    data["heroes"] = heroes
    with open("heroes.json", "w") as file:
        json.dump(data, file)
    return hero


@app.delete("/api/heroes/{id}")
def delete_hero(id: int):
    with open("heroes.json", "r") as file:
        data = json.load(file)
    heroes = data["heroes"]
    hero = next((hero for hero in heroes if hero["id"] == id), None)
    if hero:
        heroes.remove(hero)
        data["heroes"] = heroes
        with open("heroes.json", "w") as file:
            json.dump(data, file)
        return hero
    return None


@app.put("/api/heroes/{id}")
def update_hero(id: int, name: str):
    with open("heroes.json", "r") as file:
        data = json.load(file)
    heroes = data["heroes"]
    hero = next((hero for hero in heroes if hero["id"] == id), None)
    if hero:
        hero["name"] = name
        with open("heroes.json", "w") as file:
            json.dump(data, file)
        return hero
    return None

