AutoElastic
===========

Efficient Elasticity on Cloud Computing

AutoElastic is a PaaS-level elasticity model for high performance applications in the cloud focused in academic research. AutoElastic acts at the PaaS level of a cloud, not imposing neither modifications on the application source code nor extra definitions of rules and actions by the programmer. Furthermore, AutoElastic proposes an operation without any prior knowledge of the application, ignoring, for example, the expected time for concluding each one of its phases.

AutoElastic’s approach provides elasticity by hiding all resource reconfiguration actions from programmers, executing without any modifications in the application’s code. In particular, AutoElastic deals with applications that do not use specific deadlines for concluding the subparts. It targets message-passing applications with explicit parallelism, which use send/receive and accept/connect directives. AutoElastic offers a mechanism to set up a new VM without blocking the HPC application and offers horizontal elasticity actions only when forecasting that the application will really maintain the current behavior in the near future.

Author: Vinicius Facco Rodrigues

Email: viniciusfacco@live.com


Use this SLA to define the limits of hosts in the cloud:

<Agreement AgreementId="slaautoelastic">
    <Name>AutoElasticSLA</Name>
    <Terms>
        <MaximalHosts>10</MaximalHosts>
        <MinimalHosts>1</MinimalHosts>
    </Terms>
</Agreement>
