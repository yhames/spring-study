from locust import task, FastHttpUser

class HelloWorldUser(FastHttpUser):
    connection_timeout = 10.0
    network_timeout = 10.0

    @task
    def hello(self):
        self.client.get("/hello")
