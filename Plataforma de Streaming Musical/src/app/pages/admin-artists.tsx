import { useState } from 'react';
import { ChevronLeft, Plus, Edit, User, Music, LayoutDashboard } from 'lucide-react';
import { Link } from 'react-router';
import { Input } from '../components/ui/input';
import { toast } from 'sonner';
import { Toaster } from 'sonner';

interface Artist {
  id: number;
  name: string;
  genre: string;
  songs: number;
  status: 'active' | 'inactive';
}

type Tab = 'list' | 'form';

export function AdminArtists() {
  const [artists, setArtists] = useState<Artist[]>([
    { id: 1, name: 'Luna Wave', genre: 'Electronic', songs: 12, status: 'active' },
    { id: 2, name: 'Neon Knights', genre: 'Rock', songs: 8, status: 'active' },
    { id: 3, name: 'Ocean Drive', genre: 'Pop', songs: 15, status: 'active' },
    { id: 4, name: 'Star Gazer', genre: 'Ambient', songs: 5, status: 'inactive' },
  ]);

  const [activeTab, setActiveTab] = useState<Tab>('list');
  const [selectedArtist, setSelectedArtist] = useState<Artist | null>(null);
  const [form, setForm] = useState({ name: '', genre: '' });

  const handleSave = () => {
    if (!form.name || !form.genre) {
      toast.error('Todos los campos son obligatorios');
      return;
    }
    if (selectedArtist) {
      setArtists(artists.map(a => a.id === selectedArtist.id ? { ...a, ...form } : a));
      toast.success('Artista actualizado correctamente');
    } else {
      const exists = artists.some(a => a.name.toLowerCase() === form.name.toLowerCase());
      if (exists) {
        toast.error('Este artista ya existe en el catálogo');
        return;
      }
      setArtists([...artists, { id: Date.now(), ...form, songs: 0, status: 'active' }]);
      toast.success('Artista agregado correctamente');
    }
    setForm({ name: '', genre: '' });
    setSelectedArtist(null);
    setActiveTab('list');
  };

  const handleEdit = (artist: Artist) => {
    setSelectedArtist(artist);
    setForm({ name: artist.name, genre: artist.genre });
    setActiveTab('form');
  };

  const handleDeactivate = () => {
    if (selectedArtist) {
      setArtists(artists.map(a => a.id === selectedArtist.id ? { ...a, status: 'inactive' } : a));
      toast.success('Artista dado de baja');
      setForm({ name: '', genre: '' });
      setSelectedArtist(null);
      setActiveTab('list');
    }
  };

  return (
    <div className="min-h-screen" style={{ background: '#0f0f0f' }}>
      <Toaster
        position="top-center"
        toastOptions={{
          style: { background: '#1a1a1a', color: '#f5f5f5', border: '1px solid rgba(255,255,255,0.08)' },
        }}
      />
      <div className="h-4" />

      {/* Header */}
      <header className="px-4 pt-2 pb-4">
        <div className="flex items-center gap-3 mb-4">
          <Link
            to="/admin/catalog"
            className="w-9 h-9 rounded-full flex items-center justify-center"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
          >
            <ChevronLeft size={20} style={{ color: '#f5f5f5' }} />
          </Link>
          <div>
            <p style={{ color: '#888888', fontSize: '12px' }}>Panel de administración</p>
            <h1 style={{ color: '#f5f5f5', fontSize: '20px', fontWeight: 700 }}>Gestión de Artistas</h1>
          </div>
        </div>

        {/* Admin nav */}
        <div className="flex gap-2 overflow-x-auto pb-1 scrollbar-hide">
          <Link
            to="/admin/catalog"
            className="flex-shrink-0 flex items-center gap-1.5 px-3 py-2 rounded-xl"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)', color: '#888888', fontSize: '13px', fontWeight: 600 }}
          >
            <LayoutDashboard size={14} />
            Catálogo
          </Link>
          <div
            className="flex-shrink-0 flex items-center gap-1.5 px-3 py-2 rounded-xl"
            style={{ background: '#f5c400', color: '#0f0f0f', fontSize: '13px', fontWeight: 600 }}
          >
            <User size={14} />
            Artistas
          </div>
        </div>
      </header>

      {/* Tab switcher */}
      <div className="px-4 mb-4">
        <div
          className="flex rounded-2xl p-1"
          style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
        >
          <button
            onClick={() => setActiveTab('list')}
            className="flex-1 flex items-center justify-center gap-2 py-2.5 rounded-xl transition-all"
            style={{
              background: activeTab === 'list' ? '#f5c400' : 'transparent',
              color: activeTab === 'list' ? '#0f0f0f' : '#888888',
              fontWeight: 600, fontSize: '14px',
            }}
          >
            <Music size={16} />
            Artistas
          </button>
          <button
            onClick={() => { setActiveTab('form'); setSelectedArtist(null); setForm({ name: '', genre: '' }); }}
            className="flex-1 flex items-center justify-center gap-2 py-2.5 rounded-xl transition-all"
            style={{
              background: activeTab === 'form' ? '#f5c400' : 'transparent',
              color: activeTab === 'form' ? '#0f0f0f' : '#888888',
              fontWeight: 600, fontSize: '14px',
            }}
          >
            <Plus size={16} />
            {selectedArtist ? 'Editar' : 'Agregar'}
          </button>
        </div>
      </div>

      {/* List tab */}
      {activeTab === 'list' && (
        <section className="px-4 space-y-2 pb-8">
          <div className="flex items-center justify-between mb-3">
            <span style={{ color: '#888888', fontSize: '13px' }}>{artists.length} artistas</span>
            <button
              onClick={() => { setActiveTab('form'); setSelectedArtist(null); setForm({ name: '', genre: '' }); }}
              className="flex items-center gap-1.5 px-3 py-1.5 rounded-xl"
              style={{ background: '#f5c400', color: '#0f0f0f', fontSize: '13px', fontWeight: 600 }}
            >
              <Plus size={15} />
              Nuevo
            </button>
          </div>

          {artists.map(artist => (
            <div
              key={artist.id}
              className="flex items-center gap-3 p-3 rounded-2xl"
              style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
            >
              <div
                className="w-11 h-11 rounded-xl flex-shrink-0 flex items-center justify-center"
                style={{ background: '#2a2a2a' }}
              >
                <User size={18} style={{ color: '#f5c400' }} />
              </div>

              <div className="flex-1 min-w-0">
                <p style={{ color: '#f5f5f5', fontSize: '14px', fontWeight: 600 }} className="truncate">
                  {artist.name}
                </p>
                <p style={{ color: '#888888', fontSize: '12px' }}>
                  {artist.genre} • {artist.songs} canciones
                </p>
              </div>

              <span
                className="px-2 py-0.5 rounded-full flex-shrink-0"
                style={{
                  background: artist.status === 'active' ? 'rgba(245,196,0,0.15)' : 'rgba(229,62,62,0.15)',
                  color: artist.status === 'active' ? '#f5c400' : '#e53e3e',
                  fontSize: '11px', fontWeight: 600,
                }}
              >
                {artist.status === 'active' ? 'Activo' : 'Inactivo'}
              </span>

              <button onClick={() => handleEdit(artist)} className="flex-shrink-0">
                <Edit size={16} style={{ color: '#f5c400' }} />
              </button>
            </div>
          ))}
        </section>
      )}

      {/* Form tab */}
      {activeTab === 'form' && (
        <section className="px-4 pb-8">
          <div
            className="rounded-2xl p-4"
            style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
          >
            <h2 style={{ color: '#f5f5f5', fontSize: '16px', fontWeight: 700, marginBottom: '16px' }}>
              {selectedArtist ? 'Editar Artista' : 'Nuevo Artista'}
            </h2>
            <div className="space-y-4">
              <Input
                label="Nombre del artista"
                placeholder="Ej: Luna Wave"
                value={form.name}
                onChange={(e) => setForm({ ...form, name: e.target.value })}
              />
              <Input
                label="Género musical"
                placeholder="Ej: Electronic"
                value={form.genre}
                onChange={(e) => setForm({ ...form, genre: e.target.value })}
              />
              <div className="space-y-2 pt-2">
                <button
                  onClick={handleSave}
                  className="w-full py-3 rounded-2xl"
                  style={{ background: '#f5c400', color: '#0f0f0f', fontWeight: 700, fontSize: '15px' }}
                >
                  {selectedArtist ? 'Guardar Cambios' : 'Agregar Artista'}
                </button>
                {selectedArtist && selectedArtist.status === 'active' && (
                  <button
                    onClick={handleDeactivate}
                    className="w-full py-3 rounded-2xl"
                    style={{ background: 'rgba(229,62,62,0.15)', color: '#e53e3e', fontWeight: 600, fontSize: '15px' }}
                  >
                    Dar de baja
                  </button>
                )}
                <button
                  onClick={() => { setActiveTab('list'); setSelectedArtist(null); setForm({ name: '', genre: '' }); }}
                  className="w-full py-3 rounded-2xl"
                  style={{ background: '#2a2a2a', color: '#888888', fontWeight: 600, fontSize: '15px' }}
                >
                  Cancelar
                </button>
              </div>
            </div>
          </div>
        </section>
      )}
    </div>
  );
}
