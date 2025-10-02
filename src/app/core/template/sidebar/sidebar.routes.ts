export interface RouteInfo {
  path: string;
  title: string;
  icon: string;
}

export const ROUTES: RouteInfo[] = [
  { path: '/ads', title: 'Liste des annonces', icon: 'pi pi-list' },
  { path: '/ads/new', title: 'Créer une annonce', icon: 'pi pi-plus' }
];
