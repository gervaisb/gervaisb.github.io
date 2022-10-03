# A modular monolith

The term _monolith_ describe a software that is depoyed as one. One unique artifact that contains all features.

In the era of micro services the monolith has a negative connotation and is usually associated with the _big ball of mud_. But not all monoliths are unmaintainable mess. The _modular monolith_ is a specific one build around the idea of having different _modules_ that should be independent from the others, as much as possible. You should be able to remove a module without breaking the others. This modularity will help in structuring the code but a _modular monolith_ has some other advantages that we cannot ignore:



## Performance

Messages between the modules of a monoliths are exchanged in-memory without serialization and network latency. They are bare metal function invocations and are exchanged almost immediatley. There is no extra delay implied when exchanging messages inside a monolith. 

## Simplicity

It is more easier to reason about a single code base than a couple of distributed services. There is no need to trace requests accross the wire, a stacktrace contains all the calls that leads to an error. 

Because you deploy everything in one artifact there is only one stack required to run it. This is way more easier to maintain. You also reduce the variety of languages, frameworks and librairies.  This will improve the coherence of your sytem an code style and simplify the on-boarding of new developers. 

## Type safety

When you change a message the compilation is likely to fail, you don't have to wait the integration tests to discover an issue. It is also easier to refactor and navigate into your code base when messages are typed.

With a strongly typed language you can use the compiler to verify that all modules will understand each other as expected. "If it compile, it will run". No need for slow integration tests to validate that part (but they are still required to validate the behavior). 

## Lighter datas

While all modules should be independent the need to duplicate the data between your modules is much less than into a distributed system where each service must own the required data. In a monolith there is a hughe probability that you will use one database. And nothing prevent you to join a table of another module instead of duplicating the data. 

## Scalability

If your application reach a point where you need to distribute it to cope with an increasing load you can use the type system to identify the relations between your modules. Because those modules are decoupled you should be able to easily expose the critical ones as services that will scale better. At least you know which one is critical and don't have invested time and money on assumptions.





Of course there are some drawbacks too. Typed messages cannot evolve easily. Require good review process to avoid breaking another module or writing in a database schema that is not owned by teh module. One single language restrict the possibilities of improvement and you may miss some profiles that do not know the stack. 

--

reference to simon brown pour introduire le terme "modular monolith"