export const hasRole = (auth, role) => {
  return auth?.roles?.includes(role);
};

export const hasAnyRole = (auth, roles = []) => {
  return roles.some((r) => auth?.roles?.includes(r));
};
