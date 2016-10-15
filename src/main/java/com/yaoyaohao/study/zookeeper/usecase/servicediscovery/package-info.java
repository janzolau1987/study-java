package com.yaoyaohao.study.zookeeper.usecase.servicediscovery;

/*
 * 基于ZooKeeper提供服务发现服务- Service Discovery
 * 
	Service discovery is one of the key components of distributed systems and
service-oriented architectures where services need to find each other. In the simplest
way, service discovery helps clients determine the IP and port for a service that exists
on multiple hosts. One common example of this is how a web service can find the
right host that serves a caching service in the network. At first glance, it appears that
we can use a Domain Name System (DNS) service as a service discovery system.
However, a solution with DNS is not viable when the service locations change
frequently due to auto or manual scaling, new deployments of services, or when
services are failed over or replaced with newer hosts due to host failures.
	Important properties of a service discovery system are mentioned here:
		• It allows services to register their availability
		• It provides a mechanism to locate a live instance of a particular service
		• It propagates a service change notification when the instances of a
		service change
	Let /services represent the base path in the ZooKeeper tree for services of the
system or platform. Persistent znodes under /services designate services available
to be consumed by clients.
	A simple service discovery model with ZooKeeper is illustrated as follows:
		• Service registration: For service registrations, hosts that serve a particular
		service create an ephemeral znode in the relevant path under /services.
		For example, if a server is hosting a web-caching service, it creates an
		ephemeral znode with its hostname in /services/web_cache. Again, if
		some other server hosts a file-serving service, it creates another ephemeral
		znode with its hostname in /services/file_server and so on.
		• Service discovery: Now, clients joining the system, register for watches in the
		znode path for the particular service. If a client wants to know the servers
		in the infrastructure that serve a web-caching service, the client will keep a
		watch in /services/web_cache.
	If a new host is added to serve web caching under this path, the client will
automatically know the details about the new location. Again, if an existing
host goes down, the client gets the event notification and can take the
necessary action of connecting to another host.
	A service discovery system provides a seamless mechanism to guarantee service
continuity in the case of failures and is an indispensable part of building a robust
and scalable distributed platform. Apache Curator provides an extension called
curator-x-discovery in its ZooKeeper library; this implements a service registration
and discovery model. It also provides a service discovery server called curatorx-
discovery-server that exposes a RESTful web service to register, remove, and
query services for non-Java or legacy applications to use the service discovery
functionalities.
 */
