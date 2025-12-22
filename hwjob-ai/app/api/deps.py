import os
import jwt
from fastapi import Header, HTTPException, status, Depends

SECRET_KEY = os.getenv('SECRET_KEY')
