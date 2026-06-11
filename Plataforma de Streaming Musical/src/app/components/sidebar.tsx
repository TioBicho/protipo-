import { LayoutDashboard, Music, Users, Settings, LogOut } from 'lucide-react';
import { Link, useLocation } from 'react-router';

const menuItems = [
  { icon: LayoutDashboard, label: 'Dashboard', path: '/admin/catalog' },
  { icon: Music, label: 'Catálogo', path: '/admin/catalog' },
  { icon: Users, label: 'Usuarios', path: '/admin/users' },
  { icon: Settings, label: 'Configuración', path: '/admin/settings' },
];

export function Sidebar() {
  const location = useLocation();
  
  return (
    <div className="w-64 bg-zinc-950 border-r border-zinc-800 h-screen flex flex-col">
      <div className="p-6 border-b border-zinc-800">
        <h1 className="text-xl text-white flex items-center gap-2">
          <Music className="text-violet-500" />
          <span>Admin Panel</span>
        </h1>
      </div>
      
      <nav className="flex-1 p-4">
        <ul className="space-y-2">
          {menuItems.map((item) => {
            const Icon = item.icon;
            const isActive = location.pathname === item.path;
            
            return (
              <li key={item.path}>
                <Link
                  to={item.path}
                  className={`flex items-center gap-3 px-4 py-3 rounded-lg transition-colors ${
                    isActive
                      ? 'bg-violet-600 text-white'
                      : 'text-zinc-400 hover:bg-zinc-900 hover:text-white'
                  }`}
                >
                  <Icon size={20} />
                  <span>{item.label}</span>
                </Link>
              </li>
            );
          })}
        </ul>
      </nav>
      
      <div className="p-4 border-t border-zinc-800">
        <button className="flex items-center gap-3 px-4 py-3 rounded-lg text-zinc-400 hover:bg-zinc-900 hover:text-white transition-colors w-full">
          <LogOut size={20} />
          <span>Cerrar sesión</span>
        </button>
      </div>
    </div>
  );
}
