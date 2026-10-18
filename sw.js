self.addEventListener('notificationclick', event => {
  event.notification.close();
  event.waitUntil(clients.matchAll({ type: 'window', includeUncontrolled: true }).then(list => {
    const existing = list.find(client => client.url.includes(self.location.origin));
    return existing ? existing.focus() : clients.openWindow('/#heartbeat');
  }));
});
