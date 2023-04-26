const RandomUser = require("randomuser");

const randomClient = new RandomUser();

const generateRandomName = () => (Math.random() + 1).toString(36).substring(2);

const getRandomUsers = (quantity) => {
  return new Promise((resolve) => {
    randomClient.getUsers({ results: quantity }, (data) => {
      const userShorten = data.map(({ name, login, email }) => ({
        name: `${name.first} ${name.last}`,
        email,
        password: login.password,
      }));
      resolve(userShorten);
    });
  });
};

module.exports = { generateRandomName, getRandomUsers };
