"""Angel One SmartAPI integration for the NSE Analyzer backend.
Credentials are supplied at runtime; never store API keys in source control.
"""
import os
from typing import Any, Dict
from SmartApi import SmartConnect


class AngelOneClient:
    def __init__(self) -> None:
        self.api_key = os.environ["ANGEL_API_KEY"]
        self.client_code = os.environ["ANGEL_CLIENT_CODE"]
        self.pin = os.environ["ANGEL_PIN"]
        self.totp_secret = os.environ["ANGEL_TOTP_SECRET"]
        self.smart = SmartConnect(api_key=self.api_key)
        self.refresh_token = None
        self.feed_token = None

    def login(self) -> Dict[str, Any]:
        import pyotp
        totp = pyotp.TOTP(self.totp_secret).now()
        data = self.smart.generateSession(self.client_code, self.pin, totp)
        if not data or not data.get("status"):
            raise RuntimeError(data or "Angel One login failed")
        self.refresh_token = data["data"]["refreshToken"]
        self.feed_token = self.smart.getfeedToken()
        return data["data"]

    def ltp(self, exchange: str, tradingsymbol: str, symboltoken: str) -> Dict[str, Any]:
        return self.smart.ltpData(exchange, tradingsymbol, symboltoken)

    def quote(self, exchange_tokens: Dict[str, list], mode: str = "FULL") -> Dict[str, Any]:
        return self.smart.getMarketData(mode, exchange_tokens)

    def logout(self) -> Any:
        return self.smart.terminateSession(self.client_code)
