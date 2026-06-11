import { Play, Heart, ListMusic, Sparkles, Search, Bell } from 'lucide-react';
import { useState } from 'react';
import { Player } from '../components/player';
import { Link } from 'react-router';

const recommendedPlaylists = [
  { id: 'r1', name: 'Tus Éxitos', songs: 10, color: 'from-yellow-400 to-amber-600' },
  { id: 'r2', name: 'Tarde Chill', songs: 8, color: 'from-amber-500 to-orange-500' },
  { id: 'r3', name: 'Energía Máx', songs: 14, color: 'from-yellow-300 to-amber-500' },
  { id: 'r4', name: 'Lo Más Nuevo', songs: 12, color: 'from-orange-400 to-yellow-500' },
];

const popularSongs = [
  { id: 1, title: 'Midnight Dreams', artist: 'Luna Wave', duration: '3:45', plays: '1.2M', color: 'from-yellow-500 to-orange-500' },
  { id: 2, title: 'Electric Soul', artist: 'Neon Knights', duration: '4:12', plays: '890K', color: 'from-yellow-400 to-yellow-600' },
  { id: 3, title: 'Summer Breeze', artist: 'Ocean Drive', duration: '3:28', plays: '2.1M', color: 'from-amber-500 to-yellow-400' },
  { id: 4, title: 'Cosmic Journey', artist: 'Star Gazer', duration: '5:20', plays: '750K', color: 'from-yellow-600 to-amber-700' },
  { id: 5, title: 'Urban Nights', artist: 'City Lights', duration: '3:55', plays: '1.5M', color: 'from-amber-400 to-orange-500' },
  { id: 6, title: 'Golden Hour', artist: 'Sunset Vibes', duration: '4:03', plays: '980K', color: 'from-yellow-300 to-amber-500' },
  { id: 7, title: 'Lost in Paradise', artist: 'Tropical Sound', duration: '3:38', plays: '1.8M', color: 'from-yellow-500 to-yellow-700' },
  { id: 8, title: 'Neon Pulse', artist: 'Synth Wave', duration: '4:25', plays: '650K', color: 'from-amber-300 to-yellow-500' },
];

const featuredAlbums = [
  { id: 1, title: 'Golden Era', artist: 'Various', color: 'from-yellow-400 to-amber-600' },
  { id: 2, title: 'Night Drive', artist: 'Synth Wave', color: 'from-amber-500 to-orange-600' },
  { id: 3, title: 'Sol Rising', artist: 'Luna Wave', color: 'from-yellow-300 to-yellow-600' },
  { id: 4, title: 'Electro Hits', artist: 'Neon Mix', color: 'from-amber-400 to-yellow-500' },
];

export function Home() {
  const [likedSongs, setLikedSongs] = useState<Set<number>>(new Set());

  const toggleLike = (id: number) => {
    setLikedSongs(prev => {
      const next = new Set(prev);
      next.has(id) ? next.delete(id) : next.add(id);
      return next;
    });
  };

  return (
    <div className="min-h-screen pb-36" style={{ background: '#0f0f0f' }}>
      {/* Status bar space */}
      <div className="h-4" />

      {/* Header */}
      <header className="px-4 pt-2 pb-4">
        <div className="flex items-center justify-between mb-5">
          <div>
            <p style={{ color: '#888888', fontSize: '13px' }}>Bienvenido de vuelta</p>
            <h1 style={{ color: '#f5f5f5', fontSize: '22px', fontWeight: 700, lineHeight: 1.2 }}>
              Descubre Música
            </h1>
          </div>
          <div className="flex items-center gap-2">
            <button
              className="w-9 h-9 rounded-full flex items-center justify-center"
              style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
            >
              <Bell size={18} style={{ color: '#f5c400' }} />
            </button>
            <div
              className="w-9 h-9 rounded-full flex items-center justify-center"
              style={{ background: '#f5c400' }}
            >
              <span style={{ color: '#0f0f0f', fontSize: '14px', fontWeight: 700 }}>U</span>
            </div>
          </div>
        </div>

        {/* Search Bar */}
        <div
          className="flex items-center gap-3 px-4 py-3 rounded-2xl"
          style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
        >
          <Search size={18} style={{ color: '#888888' }} />
          <span style={{ color: '#555555', fontSize: '15px' }}>Buscar canciones, artistas...</span>
        </div>
      </header>

      {/* AI Suggestions Banner */}
      <div className="px-4 mb-6">
        <Link to="/ia-suggestions">
          <div
            className="rounded-2xl p-4 flex items-center justify-between"
            style={{ background: 'linear-gradient(135deg, #f5c400 0%, #e8a000 100%)' }}
          >
            <div className="flex items-center gap-3">
              <div
                className="w-10 h-10 rounded-xl flex items-center justify-center"
                style={{ background: 'rgba(0,0,0,0.15)' }}
              >
                <Sparkles size={20} style={{ color: '#0f0f0f' }} />
              </div>
              <div>
                <p style={{ color: '#0f0f0f', fontWeight: 700, fontSize: '14px', lineHeight: 1.2 }}>
                  Sugerencias IA
                </p>
                <p style={{ color: 'rgba(0,0,0,0.6)', fontSize: '12px' }}>
                  Música personalizada para ti
                </p>
              </div>
            </div>
            <span
              className="px-4 py-2 rounded-xl"
              style={{ background: 'rgba(0,0,0,0.15)', color: '#0f0f0f', fontSize: '13px', fontWeight: 600 }}
            >
              Explorar
            </span>
          </div>
        </Link>
      </div>

      {/* Recommended Playlists (HU08) */}
      <section className="mb-6">
        <div className="px-4 flex items-center justify-between mb-3">
          <h2 style={{ color: '#f5f5f5', fontSize: '17px', fontWeight: 700 }}>Playlists Recomendadas</h2>
          <Link to="/ia-suggestions">
            <span style={{ color: '#f5c400', fontSize: '13px', fontWeight: 600 }}>Ver más</span>
          </Link>
        </div>
        <div className="px-4 flex gap-3 overflow-x-auto pb-2 scrollbar-hide">
          {recommendedPlaylists.map((pl) => (
            <div key={pl.id} className="flex-shrink-0 w-32">
              <div
                className={`w-32 h-32 rounded-2xl bg-gradient-to-br ${pl.color} mb-2 flex items-center justify-center`}
              >
                <Sparkles size={22} style={{ color: 'rgba(0,0,0,0.3)' }} />
              </div>
              <p style={{ color: '#f5f5f5', fontSize: '12px', fontWeight: 600, lineHeight: 1.3 }}>{pl.name}</p>
              <p style={{ color: '#888888', fontSize: '11px' }}>{pl.songs} canciones</p>
            </div>
          ))}
        </div>
      </section>

      {/* Featured Albums */}
      <section className="mb-6">
        <div className="px-4 flex items-center justify-between mb-3">
          <h2 style={{ color: '#f5f5f5', fontSize: '17px', fontWeight: 700 }}>Destacados</h2>
          <span style={{ color: '#f5c400', fontSize: '13px', fontWeight: 600 }}>Ver todo</span>
        </div>
        <div className="px-4 flex gap-3 overflow-x-auto pb-2 scrollbar-hide">
          {featuredAlbums.map((album) => (
            <div key={album.id} className="flex-shrink-0 w-36">
              <div
                className={`w-36 h-36 rounded-2xl bg-gradient-to-br ${album.color} mb-2 flex items-center justify-center`}
              >
                <Play size={28} style={{ color: 'rgba(0,0,0,0.5)' }} fill="rgba(0,0,0,0.3)" />
              </div>
              <p style={{ color: '#f5f5f5', fontSize: '13px', fontWeight: 600, lineHeight: 1.3 }}>{album.title}</p>
              <p style={{ color: '#888888', fontSize: '12px' }}>{album.artist}</p>
            </div>
          ))}
        </div>
      </section>

      {/* Popular Songs */}
      <section className="px-4">
        <div className="flex items-center justify-between mb-3">
          <h2 style={{ color: '#f5f5f5', fontSize: '17px', fontWeight: 700 }}>Canciones Populares</h2>
          <Link to="/playlists">
            <div className="flex items-center gap-1" style={{ color: '#f5c400' }}>
              <ListMusic size={16} />
              <span style={{ fontSize: '13px', fontWeight: 600 }}>Playlists</span>
            </div>
          </Link>
        </div>

        <div className="space-y-2">
          {popularSongs.map((song, index) => (
            <div
              key={song.id}
              className="flex items-center gap-3 p-3 rounded-2xl"
              style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.05)' }}
            >
              <span style={{ color: '#555555', fontSize: '13px', width: '20px', textAlign: 'center' }}>
                {index + 1}
              </span>

              <div
                className={`w-12 h-12 rounded-xl bg-gradient-to-br ${song.color} flex-shrink-0 flex items-center justify-center`}
              >
                <Play size={16} fill="rgba(0,0,0,0.4)" style={{ color: 'rgba(0,0,0,0.4)' }} />
              </div>

              <div className="flex-1 min-w-0">
                <p style={{ color: '#f5f5f5', fontSize: '14px', fontWeight: 600 }} className="truncate">
                  {song.title}
                </p>
                <p style={{ color: '#888888', fontSize: '12px' }} className="truncate">
                  {song.artist} • {song.plays}
                </p>
              </div>

              <span style={{ color: '#555555', fontSize: '12px', flexShrink: 0 }}>{song.duration}</span>

              <button
                onClick={() => toggleLike(song.id)}
                className="flex-shrink-0 w-8 h-8 flex items-center justify-center"
              >
                <Heart
                  size={18}
                  style={{
                    color: likedSongs.has(song.id) ? '#f5c400' : '#555555',
                    fill: likedSongs.has(song.id) ? '#f5c400' : 'none',
                  }}
                />
              </button>
            </div>
          ))}
        </div>
      </section>

      <Player />
    </div>
  );
}
