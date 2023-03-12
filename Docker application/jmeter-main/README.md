# jmeter
This repo contains the jmeter test plans to load test the application
<br></br>

We expect the following variable to be passed while running the container
 - host

**host** refers to where the application is running.

If the application is running in localhost or in a container ported to localhost then the value for host should be set to **host.docker.internal**


## Commands to run
- Clone the repo
  - `git clone git@github.com:INFO7205-Group5/jmeter.git`
- To run a container
  - `docker run --name jmeter -v $(pwd):$(pwd) -w $(pwd) -e host="host.docker.internal"  info7205group5/jmeter:latest`

