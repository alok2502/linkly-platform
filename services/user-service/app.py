from flask import Flask, jsonify, request
import os

app = Flask(__name__)

# in-memory user store (a real app would use Postgres)
users = {}

@app.route("/health")
def health():
    return jsonify(status="healthy", service="user-service")

@app.route("/users", methods=["POST"])
def create_user():
    data = request.get_json() or {}
    username = data.get("username")
    if not username:
        return jsonify(error="username required"), 400
    users[username] = {"username": username}
    return jsonify(users[username]), 201

@app.route("/users/<username>")
def get_user(username):
    user = users.get(username)
    if not user:
        return jsonify(error="not found"), 404
    return jsonify(user)

if __name__ == "__main__":
    port = int(os.getenv("PORT", "5000"))
    app.run(host="0.0.0.0", port=port)
