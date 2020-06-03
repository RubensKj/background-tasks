<p align="left">
   <img src=".github/background_task_logo.png" width="100"/>
</p>

# Background Tasks

[![Author](https://img.shields.io/badge/author-RubensKj-00cc74?style=flat-square)](https://github.com/RubensKj)
[![Languages](https://img.shields.io/github/languages/count/RubensKj/background-tasks?color=00cc74&style=flat-square)](#)
[![License](https://img.shields.io/github/license/RubensKj/background-tasks?color=00cc74&style=flat-square)](https://github.com/RubensKj/background-tasks/blob/master/LICENSE)
[![Stars](https://img.shields.io/github/stars/RubensKj/background-tasks?color=00cc74&style=flat-square)](https://github.com/RubensKj/background-tasks/stargazers)
[![Forks](https://img.shields.io/github/forks/RubensKj/background-tasks?color=00cc74&style=flat-square)](https://github.com/RubensKj/background-tasks/network/members)
[![Contributors](https://img.shields.io/github/contributors/RubensKj/background-tasks?color=00cc74&style=flat-square)](https://github.com/RubensKj/background-tasks/graphs/contributors)


> Creating background tasks with subscribe 🧶

<p>Background Tasks is a library that provide a subscribe method with a callback, it will run the callback after you subscribe, however all things will be run in a second thread.</p>

# 🐦 Direct Links
 * 🏗 [Dependencies](#building_construction-dependencies)
 * 🚀 [Getting Started](#rocket-getting-started)
 * 🎉 [Want to Contribute?](#tada-want-to-contribute)
 * 📕 [License](#closed_book-license)


# :building_construction: Dependencies

- You should have at least `Java 1.8` in your computer
- Maven

# :rocket: Getting Started

By following this steps you can take advantage of using background tasks

Adding the dependency on pom.xml

```maven
<dependency>
   <groupId>com.github.rubenskj</groupId>
   <artifactId>background-tasks</artifactId>
   <version>0.0.1</version>
</dependency>
```

After resolving from maven, you can create a Subscribe

### Creating a Subscribe method

#### Params: 
`Subscribe name (String)`: This name will be shown in the console.
<br>
`Retry (Int)`: Times that will retry the callback if something throws any exception.
<br>
`ICallback`: The method that will be executed in another thread.
<br>
`Consumers (int)`: How many thread will have for this callback.

#### Constructor

```java
Subscribe subscribeBasic = new Subscribe(
      subName,
      ICallBack
);
```

Here is some examples of subscribe

#### Basic

```java
Subscribe subscribe = new Subscribe("Name of subscribe", this.handleCallback("passing param"));
```

---

Some variation of constructor

```java
// With retry

Subscribe subscribe1 = new Subscribe(
      "Name of subscribe",
      2,
      this.handleCallback("passing param")
);    

// With just consumers

Subscribe subscribe2 = new Subscribe(
      "Name of subscribe"
      this.handleCallback("passing param"),
      8
);

// With retry and consumers

Subscribe subscribe3 = new Subscribe(
      "Name of subscribe",
      4
      this.handleCallback("passing param"),
      8
);
```

#### Creating a ICallback

You can create inline callback in order to execute some small peace of code, create like this

```java
Subscribe subscribe = new Subscribe(Sub name, () -> {
   // Your code here
});
```

Or for big codes you can use this way 

```java
Subscribe subscribe = new Subscribe(Sub name, this::handleCallback);

// ...
// Below the method that subscribe was created
public ICallback handleCallback() {
   return () -> {
      // Your code here
   };
}
```

---

#### Passing params

You can pass some params to callback

```java
Subscribe subscribe = new Subscribe(Sub name, this.handleCallback(params...));

// ...
// Below the method that subscribe was created
public ICallback handleCallback(params...) {
   return () -> {
      // Your code here
      // using params here
   };
}
```

### Executing

After creating a subscribe, you can execute by following this 

```java
subscribe.subscribe();
```

# :tada: Want to Contribute?

Make your pull request following the [contributing](https://github.com/RubensKj/background-tasks/blob/master/CONTRIBUTING.md) instructions and I'll accept :).

# :closed_book: License

License used in this project is [MIT license](https://github.com/RubensKj/background-tasks/blob/master/LICENSE)