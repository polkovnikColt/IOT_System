const { v4: uuid4 } = require("uuid");

const { User, Device } = require("./entities");
const { getRandomUsers, generateRandomName } = require("./generateRandom");

const createDevices = (devicesNum) => {
  return new Array(devicesNum).fill(null).map(() => {
    const id = uuid4();
    return new Device(generateRandomName(), id);
  });
};

const createUsers = async (usersNum) => {
  const users = await getRandomUsers(usersNum);
  return users.map(
    ({ name, email, password }) => new User(name, email, password)
  );
};

const mapDevicesToUsers = (outerDevices, outerUsers) => {
  const devices = [...outerDevices];
  const users = [...outerUsers];
  for (let i = 0; i < devices.length; i++) {
    users[i].setDevices([devices[i].uuid]);
  }
  return users;
};

(async () => {
  const ENTITIES_NUM = 2;
  const rawUsers = await createUsers(ENTITIES_NUM);
  const devices = createDevices(ENTITIES_NUM);
  const users = mapDevicesToUsers(devices, rawUsers);

  //login users
  for (const user of users) {
    await user.registration();
    await user.login();
  }
  //devices login
  for (const device of devices) {
    await device.create();
    await device.registration();
  }

  //devices connect
  for (const user of users) {
    const personalDevices = user.devices;
    for (const device of personalDevices) {
      await user.connectToDevice(device);
    }
  }

  //devices connect
  for (const user of users) {
    const personalDevices = user.devices;
    for (const device of personalDevices) {
      await user.disconnectFromDevice(device);
    }
  }

  //devices connect
  for (const user of users) {
    const personalDevices = user.devices;
    for (const device of personalDevices) {
      await user.connectToDevice(device);
    }
  }

  //get devices
  for (const user of users) {
    const personalDevices = user.devices;
    for (const device of personalDevices) {
      await user.getAllDevices();
      await user.getDeviceById(device);
    }
  }

  for (const device of devices) {
    await device.changeStatus("ONLINE");
    await device.getStatus();
  }

  //delete devices
  for (let i = 0; i < devices.length; i += 2) {
    await devices[i].delete();
  }

  //try to get devices after delete
  for (const user of users) {
    const personalDevices = user.devices;
    for (const device of personalDevices) {
      await user.getAllDevices();
      await user.getDeviceById(device);
    }
  }
})();
