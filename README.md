# ReadSail - A Library Book Management System

ReadSail (Library Book Issue Management System) is a simple and user-friendly Java Swing application designed to help users manage their library book database effectively. Whether you're a student looking for inspiration for your school or university project or an enthusiast interested in Java Swing development, ReadSail provides a solid foundation with a sleek user interface.

## Features

1. **Add a Book**: Easily add new books to your library collection with all the necessary details.

2. **Remove a Book**: Remove books from your library database when they are no longer available.

3. **Query Whole Book Database**: Search and retrieve information from the entire book database efficiently.

4. **User-Friendly Interface**: ReadSail boasts an intuitive and visually appealing interface, making it easy for users to navigate and interact with the system.

5. **Login & Logout Functionality**: Secure your library database with login and logout functionality to ensure only authorized users can access and manage the system.

6. **Database Powered by MySQL**: ReadSail uses MySQL Server as its database engine, running in a Docker container for ease of deployment and management.

7. **Inbuilt Database Manager**: ReadSail comes with a built-in Database Manager that takes care of all the heavy lifting. No need to play architect with tables and columns! Just toss our trusty MySQL DB into the Docker ring, fire up ReadSail, and let our Database Manager do the 'boring' stuff for you. It's like having your own personal data wizard—no capes required!

## Getting Started

To get started with ReadSail, follow these steps:

1. Clone this repository to your local machine.
   ```
   git clone https://github.com/Dyr0tH/ReadSail.git
   ```

2. Install the necessary dependency [Docker](https://www.docker.com/) & the [JDBC Driver](https://dev.mysql.com/downloads/connector/j/) from official microsoft website & configure it with your IDE.

3. Set up the Database container (after replacing the default password with your password in docker-compose.yml) with the following docker command:
   ```
   docker-compose up
   ```
4. Build and run the application using your preferred Java IDE. I used Intellij <3.

5. Enjoy and modify it to your liking!!

## TODO

1. **Design & Implement the Database Manager**: Develop and finalize the Database Manager component, ensuring it handles database operations effectively.

2. **Finish the 'Remove Book' Feature**: Complete the implementation of the 'Remove Book' feature, allowing users to remove books from the library database.

3. **Implement User Authentication Feature**: Create a secure user authentication system to restrict access and manage authorized users.

Feel free to contribute to these tasks or reach out for assistance. Your contributions are highly appreciated!


## Contributing

ReadSail is an open-source project, and we welcome contributions from the community. If you'd like to contribute, it would be awesome !!

## Feedback and Support

If you have any questions, feedback, or encounter any issues while using ReadSail, please [open an issue](https://github.com/Dyr0th/Library-book-issue-management-system/issues) on our GitHub repository. We'd love to hear from you and improve this project further.

## About

ReadSail is a project aimed at helping students and developers who want to explore Java Swing and create a library book issue management system. It is intentionally kept simple and highly modifiable, allowing you to adapt it to your specific needs and preferences.

Feel free to customize and enhance ReadSail according to your requirements, and don't hesitate to reach out for assistance or guidance if needed.

Happy coding! 📚🖥️