const { ApiClient } = require("../apiClient");

class User {
  id = "";
  name = "";
  email = "";
  password = "";
  token = "";
  devices = [];
  apiClient = new ApiClient("http://localhost:8088");

  constructor(name, email, password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  registration = async () => {
    try {
      const body = {
        name: this.name,
        email: this.email,
        password: this.password,
      };
      const { data } = await this.apiClient.post(
        "api/v1/auth/registration",
        body
      );
      this.token = data.token;
    } catch (e) {
      console.log("Error", e);
    }
  };

  login = async () => {
    try {
      const { data } = await this.apiClient.post("api/v1/auth/registration", {
        token: this.token,
      });
      this.id = data.id;
    } catch (e) {
      console.log("Error", e);
    }
  };

  getAllDevices = async () => {
    try {
      const { data } = await this.apiClient.get(
        `api/v1/deviceToUser/getAll/${this.id}`
      );
      console.log(data);
    } catch (e) {
      console.log("Error", e);
    }
  };

  getDeviceById = async (uuid) => {
    try {
      const body = { userID: this.id, deviceID: uuid };
      const { data } = await this.apiClient.post(
        `api/v1/deviceToUser/getDevice`,
        body
      );
      console.log(data);
    } catch (e) {
      console.log("Error", e);
    }
  };

  connectToDevice = async (uuid) => {
    try {
      const body = { userID: this.id, deviceID: uuid };
      const { data } = await this.apiClient.post(
        "api/v1/deviceToUser/connect",
        body
      );
      console.log(data);
    } catch (e) {
      console.log("Error", e);
    }
  };

  disconnectFromDevice = async (uuid) => {
    try {
      const body = { userID: this.id, deviceID: uuid };
      const { data } = await this.apiClient.post(
        "api/v1/deviceToUser/disconnect",
        body
      );
      console.log(data);
    } catch (e) {
      console.log("Error", e);
    }
  };

  deleteDeviceManually = async (uuid) => {
    try {
      const body = { userID: this.id, deviceID: uuid };
      const { data } = await this.apiClient.post(
        "api/v1/deviceToUser/delete",
        body
      );
      console.log(data);
    } catch (e) {
      console.log("Error", e);
    }
  };
}

module.exports = { User };
