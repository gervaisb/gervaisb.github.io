# Applicability of Domain Driven Design

> The _Domain Driven Design_ is the idea that our code should match the domain of the business that we develop for. While it can be complex there are principles that we can apply to improve the quality of a simple code base.



## Principle 1, use the business language

If your client _register_ a _document_, then your code should have a `Document` with a `register` operation. When you have to discuss a feature with your clients, it is much more easier to speak the same langage. There are zero cognitive load implied by translating the business langage to your code. And when you draw some schemas, your client can more easily understand the idea when the terms are already known.

## Principle 2, value objects

A _value object_ is a type that represent an idea. It encapsulates that idea and enforce his constraints. Moreover it strengthen your type system and avoid some silly errors. A typical example is an `EmailAdress` it can be a `String` but nothing except your validation and the names can tell us that it is a valid email. An alternative to `String` is to create an  `EmailAdress` class that will represent a valid email adress.

## Principle 3, aggregates

Once we have to enforce a business constraint (not a validation rule) we usually implement it inside a service and expose all required fields from our classes. This break encapsulation and introduce the _anemic object_ pattern. 

In _DDD_, there is the notion of _aggregate_ that is reponsible to hold a constraint on which you interact. 



