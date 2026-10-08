import os
from fastapi import FastAPI, HTTPException
from .angelone_client import AngelOneClient

app = FastAPI(title="NSE Analyzer Angel One Backend")


@app.get("/health")
def health():
    return {"ok": True, "angel_configured": all(os.getenv(k) for k in (
        "ANGEL_API_KEY", "ANGEL_CLIENT_CODE", "ANGEL_PIN", "ANGEL_TOTP_SECRET"
    ))}


@app.post("/v1/angel/login")
def angel_login():
    try:
        return {"ok": True, "data": AngelOneClient().login()}
    except Exception as exc:
        raise HTTPException(status_code=502, detail=f"Angel One login failed: {exc}")


@app.post("/v1/angel/ltp")
def angel_ltp(exchange: str, tradingsymbol: str, symboltoken: str):
    try:
        return AngelOneClient().ltp(exchange, tradingsymbol, symboltoken)
    except Exception as exc:
        raise HTTPException(status_code=502, detail=f"Angel One quote failed: {exc}")
