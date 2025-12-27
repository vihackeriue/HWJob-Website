from flask import Blueprint

main_bp = Blueprint('main', __name__)


@main_bp.route("/")
def root():
    return {"message": "Hello World"}


@main_bp.route("/hello/<name>")
def say_hello(name: str):
    return {"message": f"Hello {name}"}
