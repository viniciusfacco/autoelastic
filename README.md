AutoElastic
===========

Efficient Elasticity on Cloud Computing

AutoElastic is a PaaS-level elasticity model for high performance applications in the cloud focused in academic research. AutoElastic acts at the PaaS level of a cloud, not imposing neither modifications on the application source code nor extra definitions of rules and actions by the programmer. Furthermore, AutoElastic proposes an operation without any prior knowledge of the application, ignoring, for example, the expected time for concluding each one of its phases.

AutoElastic’s approach provides elasticity by hiding all resource reconfiguration actions from programmers, executing without any modifications in the application’s code. In particular, AutoElastic deals with applications that do not use specific deadlines for concluding the subparts. It targets message-passing applications with explicit parallelism, which use send/receive and accept/connect directives. AutoElastic offers a mechanism to set up a new VM without blocking the HPC application and offers horizontal elasticity actions only when forecasting that the application will really maintain the current behavior in the near future.

Author: Vinicius Facco Rodrigues

Email: viniciusfacco@live.com

How to Use
==========
- Source code: AutoElastic/src/\*
- Main class: AutoElastic/src/autoelastic/AutoElastic.java
- Libraries: third-party-libs.rar (includes OpenNebula 4.12.1 API and additional libs)
- SLA file: autoelastic.xml. The current version of AutoElastic uses the parameteres "MaximalHosts", "MinimalHosts", MaximalVirtualMachines" and "MinimalVitualMachines" from the SLA. These values refer to how many physical or virtual machines are allowed in the cloud.
- Compiled version: you can download the compiled version "AutoElastic-Compiled.zip" available in the repository. Unzip the file and run AutoElastic.jar (java -jar AutoElastic.jar).

AutoElastic Modes
==========
AutoElastic has two execution modes depending on the arguments passed by command line:
- 0 argunments: If no argument is passed by command line, AutoElastic runs the User Interface Mode. In an user interface, all execution parameters can be setted and there are a possibility to save these parameters in a XML file to load them latter or pass them by argument to AutoElastic.jar.
- 1 argument: If one argument is passed by command line, AutoElastic runs the Command Line Mode. This argument must be a XML file with all parameters. To generate this file you can run AutoElastic firstly in the User Interface Mode and then set parameters and save a XML file. After that, you can edit this file and change any parameter if you wish.

Extras
==========
- Application for run experiments in the cloud: https://github.com/viniciusfacco/FunctionProcessing
