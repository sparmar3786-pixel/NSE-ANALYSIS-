from backend.app import app

def test_health_endpoint_exists():
    routes = {r.path for r in app.routes}
    assert "/health" in routes
    assert "/v1/angel/login" in routes
    assert "/v1/angel/ltp" in routes
