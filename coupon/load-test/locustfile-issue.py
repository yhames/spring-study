import random
from locust import task, FastHttpUser


class CouponIssue(FastHttpUser):
    connection_timeout = 10.0
    network_timeout = 10.0

    @task
    def issue(self):
        payload = {
            "user_id": random.randint(1, 10_000_000),  # Simulating user ID
            "coupon_id": 1
        }
        with self.rest("POST", "/v1/issue", json=payload):
            pass
