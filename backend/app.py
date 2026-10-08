import os
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from .angelone_client import AngelOneClient

app = FastAPI(title="NSE Analyzer Angel One Backend")
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=False,
    allow_methods=["*"],
    allow_headers=["*"],
)

_client = None

def client():
    global _client
    if _client is None:
        _client = AngelOneClient()
    return _client

@app.get("/health")
def health():
    return {"ok": True, "angel_configured": all(os.getenv(k) for k in (
        "ANGEL_API_KEY", "ANGEL_CLIENT_CODE", "ANGEL_PIN", "ANGEL_TOTP_SECRET"
    ))}

@app.post("/v1/angel/login")
def angel_login():
    try:
        return {"ok": True, "data": client().login()}
    except Exception as exc:
        raise HTTPException(status_code=502, detail=f"Angel One login failed: {exc}")

@app.post("/v1/angel/ltp")
def angel_ltp(exchange: str, tradingsymbol: str, symboltoken: str):
    try:
        c = client()
        if not c.refresh_token:
            c.login()
        return c.ltp(exchange, tradingsymbol, symboltoken)
    except Exception as exc:
        raise HTTPException(status_code=502, detail=f"Angel One quote failed: {exc}")
